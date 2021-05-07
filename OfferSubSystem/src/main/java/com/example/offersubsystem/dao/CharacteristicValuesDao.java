package com.example.offersubsystem.dao;

import com.example.offersubsystem.entities.CharacteristicValue;
import com.example.offersubsystem.entities.Characteristic;
import com.example.offersubsystem.entities.Offer;
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

            for(Offer offer : original.getOffersList())
                offer.getCharacteristics().remove(original);

            for(Characteristic characteristic : original.getCharacteristics())
                characteristic.getCharacteristicValues().remove(original);

            original.getOffersList().clear();
            original.getCharacteristics().clear();

            for (Offer offer : value.getOffersList()) {
                original.getOffersList().add(offer);
                offer.getCharacteristicValues().add(original);
            }

            for (Characteristic characteristic : value.getCharacteristics()) {
                original.getCharacteristics().add(characteristic);
                characteristic.getCharacteristicValues().add(original);
            }

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        CharacteristicValue value = entityManager.find(CharacteristicValue.class, id);

        if (value != null) {
            for(Offer offer : value.getOffersList())
                offer.getCharacteristicValues().remove(value);

            for(Characteristic characteristic : value.getCharacteristics())
                characteristic.getCharacteristicValues().remove(value);

            value.getOffersList().clear();
            value.getCharacteristics().clear();
            entityManager.remove(value);
        }
    }
}
