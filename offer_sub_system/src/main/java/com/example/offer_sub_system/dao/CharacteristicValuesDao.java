package com.example.offer_sub_system.dao;

import com.example.offer_sub_system.entities.CharacteristicValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class CharacteristicValuesDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CharacteristicValue> getAll() {
        return entityManager.createQuery("from CharacteristicValue", CharacteristicValue.class).getResultList();
    }

    public CharacteristicValue getById(int id) {
        return entityManager.find(CharacteristicValue.class, id);
    }

    public CharacteristicValue create(CharacteristicValue value) {
        entityManager.persist(value);
        return value;
    }

    public CharacteristicValue update(int id, CharacteristicValue value) {
        CharacteristicValue original = entityManager.find(CharacteristicValue.class, id);
        if (original != null) {

            original.setValue(value.getValue());

            original.getOfferCharacteristics().clear();

            original.setOfferCharacteristics(value.getOfferCharacteristics());


            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        CharacteristicValue value = entityManager.find(CharacteristicValue.class, id);

        if (value != null) {

            value.getOfferCharacteristics().clear();

            entityManager.remove(value);
        }
    }

    public CharacteristicValue getByName(String termName) {
        List<?> resultList = entityManager.createQuery("SELECT characteristicValue from CharacteristicValue characteristicValue where characteristicValue.value = ?1")
                .setParameter(1, termName)
                .getResultList();
        if (resultList.size() == 0)
            return null;
        else
            return (CharacteristicValue) resultList.get(0);
    }

//    public CharacteristicValue getIfExsist(CharacteristicValue characteristicValue, Characteristic characteristic, Offer offer) {
////        List<CharacteristicValue> resultList = entityManager.createQuery("from CharacteristicValue where value_id="+characteristicValue.getValue_id()+
////                "and characteristic_id="+characteristic.getCharacteristic_id()+
////                "and offer_id"
////
////                , CharacteristicValue.class).getResultList();
//        if (resultList.size() == 0)
//            return null;
//        else
//            return resultList.get(0);
//    }
}
