package com.example.offersubsystem.services;

import com.example.offersubsystem.dao.CharacteristicsDao;
import com.example.offersubsystem.dto.CharacteristicDto;
import com.example.offersubsystem.entities.Category;
import com.example.offersubsystem.entities.Characteristic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacteristicService {
    CharacteristicsDao characteristicsDao;

    public CharacteristicService(CharacteristicsDao characteristicsDao) {
        this.characteristicsDao = characteristicsDao;
    }

    public List<Characteristic> getAll() {
        return characteristicsDao.getAll();
    }

    public Characteristic getById(int id) {
        return characteristicsDao.getById(id);
    }

    public Characteristic create(Characteristic characteristic){

        return characteristicsDao.create(characteristic);
    }

    public Characteristic update(int id, Characteristic characteristic){
        return characteristicsDao.update(id, characteristic);
    }

    public void delete(int id){
        characteristicsDao.delete(id);
    }

    public Characteristic updateWithDto(CharacteristicDto characteristicDto) {
        Characteristic characteristic = new Characteristic();
        characteristic.setName(characteristicDto.getName());
        characteristic.setDescription(characteristicDto.getDescription());

        return update(characteristicDto.getCharacteristic_id(), characteristic);
    }
}
