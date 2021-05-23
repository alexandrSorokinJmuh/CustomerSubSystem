package com.example.offer_sub_system.dao;

import com.example.offer_sub_system.entities.Characteristic;
import com.example.offer_sub_system.entities.CharacteristicValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class CharacteristicsDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Characteristic> getAll() {
        return entityManager.createQuery("SELECT characteristic from Characteristic characteristic", Characteristic.class).getResultList();
    }

    public Characteristic getById(int id) {
        return entityManager.find(Characteristic.class, id);
    }

    public Characteristic create(Characteristic characteristic) {
        entityManager.persist(characteristic);
        return characteristic;
    }

    public Characteristic update(int id, Characteristic characteristic) {
        Characteristic original = entityManager.find(Characteristic.class, id);
        if (original != null) {

            original.setName(characteristic.getName());
            original.setDescription(characteristic.getDescription());


            original.getOfferCharacteristics().clear();

            original.setOfferCharacteristics(characteristic.getOfferCharacteristics());

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Characteristic characteristic = entityManager.find(Characteristic.class, id);

        if (characteristic != null) {

            characteristic.getOfferCharacteristics().clear();

            entityManager.remove(characteristic);
        }
    }

    public List<Characteristic> getCharacteristicNotLike(List<Integer> characteristics, String term) {
        List<?> resultList;
        if (characteristics.size() > 0) {
            resultList = entityManager.createQuery("SELECT characteristic from Characteristic characteristic " +
                    "where characteristic.characteristic_id not in ?1 and characteristic.name like ?2")
                    .setParameter(1, characteristics)
                    .setParameter(2, "%" + term + "%")
                    .getResultList();
        }else{
             resultList = entityManager.createQuery("SELECT characteristic from Characteristic characteristic " +
                    "where characteristic.name like ?1")
                    .setParameter(1, "%"+term+"%")
                    .getResultList();
        }
        return (List<Characteristic>) resultList;
    }
}
