package com.example.offersubsystem.services;

import com.example.offersubsystem.dao.CategoriesDao;
import com.example.offersubsystem.dao.CharacteristicValuesDao;
import com.example.offersubsystem.dao.CharacteristicsDao;
import com.example.offersubsystem.dao.OfferDao;
import com.example.offersubsystem.dto.OfferDto;
import com.example.offersubsystem.dto.PaidTypeDto;
import com.example.offersubsystem.entities.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {
    OfferDao offerDao;
    CharacteristicsDao characteristicsDao;
    CharacteristicValuesDao characteristicValuesDao;
    CategoriesDao categoriesDao;

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
