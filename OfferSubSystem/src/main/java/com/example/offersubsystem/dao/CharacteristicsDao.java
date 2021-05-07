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
public class CharacteristicsDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Characteristic> getAll() {
        return entityManager.createQuery("from Characteristic", Characteristic.class).getResultList();
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

            for(Offer offer : original.getOffersList())
                offer.getCharacteristics().remove(original);

            for(CharacteristicValue values : original.getCharacteristicValues())
                values.getCharacteristics().remove(original);

            original.getOffersList().clear();
            original.getCharacteristicValues().clear();

            for (Offer offer : characteristic.getOffersList()) {
                original.getOffersList().add(offer);
                offer.getCharacteristics().add(original);
            }

            for (CharacteristicValue value : characteristic.getCharacteristicValues()) {
                original.getCharacteristicValues().add(value);
                value.getCharacteristics().add(original);
            }

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Characteristic characteristic = entityManager.find(Characteristic.class, id);

        if (characteristic != null) {
            for(Offer offer : characteristic.getOffersList())
                offer.getCharacteristics().remove(characteristic);

            for(CharacteristicValue value : characteristic.getCharacteristicValues())
                value.getCharacteristics().remove(characteristic);

            characteristic.getOffersList().clear();
            characteristic.getCharacteristicValues().clear();
            entityManager.remove(characteristic);
        }
    }
}
