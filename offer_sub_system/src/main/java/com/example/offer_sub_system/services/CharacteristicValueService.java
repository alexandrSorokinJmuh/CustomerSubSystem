package com.example.offer_sub_system.services;

import com.example.offer_sub_system.dao.CharacteristicValuesDao;
import com.example.offer_sub_system.entities.CharacteristicValue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacteristicValueService {
    CharacteristicValuesDao characteristicValuesDao;

    public CharacteristicValueService(CharacteristicValuesDao characteristicValuesDao) {
        this.characteristicValuesDao = characteristicValuesDao;
    }

    public List<CharacteristicValue> getAll() {
        return characteristicValuesDao.getAll();
    }

    public CharacteristicValue getById(int id) {
        return characteristicValuesDao.getById(id);
    }

    public CharacteristicValue create(CharacteristicValue characteristicValue){

        return characteristicValuesDao.create(characteristicValue);
    }

    public CharacteristicValue update(int id, CharacteristicValue characteristicValue){
        return characteristicValuesDao.update(id, characteristicValue);
    }

    public void delete(int id){
        characteristicValuesDao.delete(id);
    }

}
