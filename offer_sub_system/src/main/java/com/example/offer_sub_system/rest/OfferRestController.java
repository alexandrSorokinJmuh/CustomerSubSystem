package com.example.offer_sub_system.rest;

import com.example.offer_sub_system.dto.LabelAndValueDto;
import com.example.offer_sub_system.dto.OfferDto;
import com.example.offer_sub_system.dto.PaidTypeDto;
import com.example.offer_sub_system.entities.Offer;
import com.example.offer_sub_system.services.CharacteristicService;
import com.example.offer_sub_system.services.OfferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferRestController {
    OfferService offerService;
    CharacteristicService characteristicService;

    @Value("${http.address}")
    String originAddress;
    @Value("${http.customer.address}")
    String customerOriginAddress;
    @Value("${http.order.address}")
    String orderOriginAddress;
    @Value("${jwt.header}")
    String jwtHeader;

    // create an instance of RestTemplate
    RestTemplate restTemplate = new RestTemplate();


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

    @GetMapping("/paid_types")
    public Map<Integer, PaidTypeDto> getPaidTypes(HttpServletRequest request) {
        String url = customerOriginAddress + "/paid_type";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String token = request.getHeader(originAddress);
        if (token == null) {
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(jwtHeader))
                    .map(cookie -> cookie.getValue())
                    .findFirst()
                    .orElse("");

        }

        headers.set(jwtHeader, token);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        // send POST request
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");

            Map<Integer, PaidTypeDto> responseBody = Arrays.stream(response.getBody())
                    .map(r -> (LinkedHashMap) r)
                    .collect(Collectors.toMap(
                            (linkedHashMap) -> Integer.parseInt(linkedHashMap.get("paid_type_id").toString()),
                            (linkedHashMap) -> new PaidTypeDto(linkedHashMap.get("paid_type_id").toString(), linkedHashMap.get("name").toString())
                    ));


            System.out.println(responseBody.getClass());
            System.out.println(responseBody.size());
            System.out.println(responseBody);
            return responseBody;
//        for (Object obj : responseBody) {
//            System.out.println(obj);
//            System.out.println(obj.getClass());
//            LinkedHashMap k = (LinkedHashMap) obj;
//
//        }
//
//        } else {
//            System.out.println("Request Failed");
//            System.out.println(response.getStatusCode());
//            return null;
//        }
        }
        return null;
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
        String url = String.format("%s/%d/paidTypes", customerOriginAddress, id);

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
                    .map(obj -> (LinkedHashMap) obj)
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

    @GetMapping("getToken")
    public String getToken(HttpServletRequest request, String email, String password) {

        if (email == null || email.isEmpty()) {
            email = request.getParameter("email");
        }
        if (password == null || password.isEmpty()) {
            password = request.getParameter("password");
        }
        String getTokenUrl = String.format("%s/api/auth/login", customerOriginAddress);

        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> tokenEntityMap = new HashMap<>();


        tokenEntityMap.put("email", email);
        tokenEntityMap.put("password", password);
        System.out.println(email);
        System.out.println(password);

        // build the request
        HttpEntity<Map<String, Object>> tokenEntity = new HttpEntity<>(tokenEntityMap, headers);

        ResponseEntity<Object> response = restTemplate.postForEntity(getTokenUrl, tokenEntity, Object.class);
        System.out.println(response.getBody());
        System.out.println(response.getStatusCode());
        if (response.getStatusCode() == HttpStatus.OK) {
            return ((HashMap) response.getBody()).get("token").toString();

        }
        return "";
    }

    @GetMapping("/createOrderByOfferAndToken")

    public ResponseEntity<?> createOrderByOfferAndToken(HttpServletRequest request,
                                                        String offer_id,
                                                        String email,
                                                        String password
    ) {

        if (offer_id == null || offer_id.isEmpty()) {
            offer_id = request.getParameter("offer_id");
        }
        int offerId = Integer.parseInt(offer_id);
        if (email == null || email.isEmpty()) {
            email = request.getParameter("email");
        }
        if (password == null || password.isEmpty()) {
            password = request.getParameter("password");
        }

        Offer offer = offerService.getById(offerId);




        String token = getToken(request, email, password);
        // request url
        String url = String.format("%s/getCustomerId", customerOriginAddress);


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
        ResponseEntity<Object> response = restTemplate.postForEntity(url, entity, Object.class);

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");
            String customerId = ((LinkedHashMap) response.getBody()).get("customer_id").toString();

            String createOrderUrl = String.format("%s/order", orderOriginAddress);

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
            } else {
                return ResponseEntity.badRequest().build();
            }

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            return ResponseEntity.badRequest().build();
        }
    }


}
