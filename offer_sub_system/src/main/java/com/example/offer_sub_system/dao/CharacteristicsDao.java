package com.example.offer_sub_system.dao;

import com.example.offer_sub_system.entities.Characteristic;
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
}
