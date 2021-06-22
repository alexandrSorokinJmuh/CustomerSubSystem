package com.example.offer_sub_system.services;

import com.example.offer_sub_system.dao.CategoriesDao;
import com.example.offer_sub_system.dao.CharacteristicValuesDao;
import com.example.offer_sub_system.dao.CharacteristicsDao;
import com.example.offer_sub_system.dao.OfferDao;
import com.example.offer_sub_system.dto.CustomerDto;
import com.example.offer_sub_system.dto.OfferDto;
import com.example.offer_sub_system.dto.PaidTypeDto;
import com.example.offer_sub_system.entities.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferService {
    OfferDao offerDao;
    CharacteristicsDao characteristicsDao;
    CharacteristicValuesDao characteristicValuesDao;
    CategoriesDao categoriesDao;
    RestTemplate restTemplate = new RestTemplate();

    @Value("${http.customer.address}")
    String customerOriginAddress;

    public OfferService(OfferDao offerDao, CharacteristicsDao characteristicsDao, CharacteristicValuesDao characteristicValuesDao, CategoriesDao categoriesDao) {
        this.offerDao = offerDao;
        this.characteristicsDao = characteristicsDao;
        this.characteristicValuesDao = characteristicValuesDao;
        this.categoriesDao = categoriesDao;
    }

    public List<Offer> getAll() {
        return offerDao.getAll();
    }

    public Offer getById(int id) {
        return offerDao.getById(id);
    }

    public Offer create(Offer offer){
        return offerDao.create(offer);
    }

    public Offer update(int id, Offer offer){
        return offerDao.update(id, offer);
    }

    public void delete(int id){
        offerDao.delete(id);
    }

    public CustomerDto getCustomerByEmail(String email) {
        String url = String.format("%s/api/auth/getByEmail?email=%s", customerOriginAddress, email);
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        Map<String, Object> map = new HashMap<>();

        map.put("email", email);


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity, LinkedHashMap.class);


        if (response.getStatusCode() == HttpStatus.OK) {

            return getCustomerDtoByLinkedHashMap(response.getBody());

        }

        return null;
    }
    public CustomerDto getCustomerDtoByLinkedHashMap(LinkedHashMap customer) {
        return new CustomerDto(
                customer.get("customer_id").toString(),
                customer.get("email").toString(),
                customer.get("password").toString(),
                customer.get("status").toString(),
                customer.get("role").toString()
        );
    }
    public Offer updateWithDto(OfferDto offerDto) {
        Offer offer = new Offer();
        offer.setName(offerDto.getName());
        offer.setPrice(offerDto.getPrice());
        Offer offer1 = offerDao.getById(offerDto.getOffer_id());
        if (offerDto.getCategory() != null)
            offer.setCategory(categoriesDao.getById(offerDto.getCategory()));
        if (offerDto.getPaidTypes() != null)
            offer.setPaidTypes(Arrays.stream(offerDto.getPaidTypes())
                    .map(paidType -> new OfferPaidType(offer1, paidType))
                    .collect(Collectors.toList())
            );


        if (offerDto.getCharacteristics() != null && offerDto.getCharacteristicValues() != null) {
            Integer[] characteristicsId = offerDto.getCharacteristics();
            String[] characteristicsValue = offerDto.getCharacteristicValues();
            List<OfferCharacteristics> offerCharacteristics = new ArrayList<>();
            for (int i = 0; i < characteristicsId.length; i++) {
                Characteristic characteristic = characteristicsDao.getById(characteristicsId[i]);

                CharacteristicValue characteristicValue = characteristicValuesDao.getByName(characteristicsValue[i]);

                if (characteristicValue == null){
                    characteristicValue = new CharacteristicValue();
                    characteristicValue.setValue(characteristicsValue[i]);
                    characteristicValue = characteristicValuesDao.create(characteristicValue);
                }
                OfferCharacteristics offerCharacteristic = new OfferCharacteristics();
                offerCharacteristic.setOffer(offer1);
                offerCharacteristic.setCharacteristic(characteristic);
                offerCharacteristic.setCharacteristicValue(characteristicValue);

                offerCharacteristics.add(offerCharacteristic);


            }
            offer.setOfferCharacteristics(offerCharacteristics);
        }

        System.out.println(offer);
        System.out.println(offer.getPaidTypes());
        return update(offerDto.getOffer_id(), offer);
    }

    public List<Offer> getOffersByPaidTypes(List<PaidTypeDto> paidTypeDtoList) {
        return offerDao.findOffersByPaidTypes(paidTypeDtoList.stream()
                .map(paidTypeDto -> Integer.parseInt(paidTypeDto.getPaid_type_id()))
                .collect(Collectors.toList())
        );
    }
}
