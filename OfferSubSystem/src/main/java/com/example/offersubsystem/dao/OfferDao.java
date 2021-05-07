package com.example.offersubsystem.dao;

import com.example.offersubsystem.entities.CharacteristicValue;
import com.example.offersubsystem.entities.Characteristic;
import com.example.offersubsystem.entities.Offer;
import com.example.offersubsystem.entities.OfferPaidType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class OfferDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Offer> getAll() {
        return entityManager.createQuery("from Offer", Offer.class).getResultList();
    }

    public Offer getById(int id) {
        return entityManager.find(Offer.class, id);
    }

    public Offer create(Offer offer) {
        entityManager.persist(offer);
        return offer;
    }

    public Offer update(int id, Offer offer) {
        Offer original = entityManager.find(Offer.class, id);
        if (original != null) {

            original.setName(offer.getName());
            original.setPrice(offer.getPrice());
            original.setCategory(offer.getCategory());


            original.getPaidTypes().clear();
            original.setPaidTypes(offer.getPaidTypes());



            for(Characteristic characteristic : original.getCharacteristics())
                characteristic.getOffersList().remove(original);

            for(CharacteristicValue values : original.getCharacteristicValues())
                values.getOffersList().remove(original);


            System.out.println("original " + original);
            System.out.println("original " + original.getPaidTypes());
            original.getCharacteristics().clear();
            original.getCharacteristicValues().clear();


            for (Characteristic characteristic : offer.getCharacteristics()) {
                original.getCharacteristics().add(characteristic);
                characteristic.getOffersList().add(original);
            }

            for (CharacteristicValue value : offer.getCharacteristicValues()) {
                original.getCharacteristicValues().add(value);
                value.getOffersList().add(original);
            }

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Offer offer = entityManager.find(Offer.class, id);

        if (offer != null) {
            for(Characteristic characteristic : offer.getCharacteristics())
                characteristic.getOffersList().remove(offer);

            for(CharacteristicValue value : offer.getCharacteristicValues())
                value.getOffersList().remove(offer);

            offer.getPaidTypes().clear();

            offer.getCharacteristics().clear();
            offer.getCharacteristicValues().clear();
            entityManager.remove(offer);
        }
    }
}
