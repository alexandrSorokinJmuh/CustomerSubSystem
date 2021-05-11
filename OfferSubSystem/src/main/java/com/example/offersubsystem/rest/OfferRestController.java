package com.example.offersubsystem.rest;

import com.example.offersubsystem.dto.CharacteristicDto;
import com.example.offersubsystem.dto.LabelAndValueDto;
import com.example.offersubsystem.dto.OfferDto;
import com.example.offersubsystem.dto.PaidTypeDto;
import com.example.offersubsystem.entities.Characteristic;
import com.example.offersubsystem.entities.Offer;
import com.example.offersubsystem.services.CharacteristicService;
import com.example.offersubsystem.services.OfferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferRestController {
    OfferService offerService;
    CharacteristicService characteristicService;

    @Value("${http.address}")
    String originAddress;
    @Value("${jwt.header}")
    String jwtHeader;

    public OfferRestController(OfferService offerService, CharacteristicService characteristicService) {
        this.offerService = offerService;
        this.characteristicService = characteristicService;
    }

    @GetMapping("/offers")
    public List<Offer> indexRest() {
        return offerService.getAll();
    }

    @GetMapping("/offer/{offer_id}")
    public Offer showRest(@PathVariable("offer_id") int id) {
        return offerService.getById(id);
    }


    @PostMapping("")
    public Offer createRest(Offer offer) {
        offerService.create(offer);
        return offer;
    }

    @PutMapping("/{offer_id}")
    public Offer updateRest(@PathVariable("offer_id") int id,
                            OfferDto offerDto
    ) {
        System.out.println(offerDto);
        Offer offer = offerService.updateWithDto(offerDto);

        return offer;
    }
    @DeleteMapping("/{offer_id}")
    public Offer deleteRest(@PathVariable("offer_id") int id) {
        Offer offer = offerService.getById(id);
        offerService.delete(id);
        return offer;
    }



    @GetMapping("/{offer_id}/characteristicsNotInOffer")

    public List<LabelAndValueDto> showCharacteristicsNotInOfferByTerm(
            @PathVariable("offer_id") int id,
            @RequestParam(value = "term", required = false, defaultValue = "") String term) {
        Offer offer = offerService.getById(id);

        List<LabelAndValueDto> suggestions = characteristicService.getSuggestionsByTerm(characteristicService.getCharacteristicNotInOffer(offer), term);
        return suggestions;
    }

    @GetMapping("/getCustomerOffers/{customer_id}")

    public List<Offer> getCustomerOffers(
            @PathVariable("customer_id") int id) {

        // request url
        String url = String.format("%s/%d/paidTypes", originAddress, id);

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        // send POST request
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);

        List<Offer> offerList;

        // check response
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());

            List<Object> responseBody = Arrays.asList(response.getBody());
            List<PaidTypeDto> paidTypeDtoList = responseBody.stream()
                    .map(obj -> (LinkedHashMap)obj)
                    .map(obj -> new PaidTypeDto(obj.get("paid_type_id").toString(), obj.get("name").toString()))
                    .collect(Collectors.toList());
            offerList = offerService.getOffersByPaidTypes(paidTypeDtoList);

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            offerList = offerService.getAll();
        }

        return offerList;


    }


    @GetMapping("/{offer_id}/getCategoryAndPrice")

    public ResponseEntity<?> getCategoryAndPrice(
            @PathVariable("offer_id") int id
    ) {

        Offer offer = offerService.getById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("category", offer.getCategory());
        map.put("price", offer.getPrice());

//        return map;
        return ResponseEntity.ok(map);


    }



    @GetMapping("/createOrderByOfferAndToken")

    public ResponseEntity<?> createOrderByOfferAndToken(
            int offer_id,
            String token
    ) {

        Offer offer = offerService.getById(offer_id);

        // request url
        String url = String.format("%s/getCustomerId", originAddress);

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<ResponseEntity> response = restTemplate.postForEntity(url, entity, ResponseEntity.class);

        // check response
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());

            ResponseEntity responseBody = response.getBody();
            if (responseBody.getStatusCode() == HttpStatus.OK){
                String customerId = ((LinkedHashMap)responseBody.getBody()).get("customer_id").toString();

                String createOrderUrl = String.format("%s/order", originAddress);

                // create headers
                headers = new HttpHeaders();
                // set `content-type` header
                headers.setContentType(MediaType.APPLICATION_JSON);
                // set `accept` header
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                // request body parameters
                map = new HashMap<>();
                map.put("offer_id", offer_id);
                map.put("customer_id", customerId);
                map.put("name", offer.getName());

                // build the request
                HttpEntity<Map<String, Object>> createOrderEntity = new HttpEntity<>(map, headers);

                ResponseEntity<Object> createOrder = restTemplate.postForEntity(createOrderUrl, createOrderEntity, Object.class);

                if (response.getStatusCode() == HttpStatus.CREATED) {
                    return ResponseEntity.ok(createOrder.getBody());
                }else {
                    return ResponseEntity.badRequest().build();
                }
            }else {
                return ResponseEntity.badRequest().build();
            }

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            return ResponseEntity.badRequest().build();
        }
    }


}
