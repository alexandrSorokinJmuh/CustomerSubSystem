package com.example.offer_sub_system.dao;

import com.example.offer_sub_system.entities.Offer;
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

            original.getOfferCharacteristics().clear();

            original.setOfferCharacteristics(offer.getOfferCharacteristics());
            System.out.println(original.getOfferCharacteristics());
            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Offer offer = entityManager.find(Offer.class, id);

        if (offer != null) {

            offer.getOfferCharacteristics().clear();

            offer.getPaidTypes().clear();

            entityManager.remove(offer);
        }
    }

    public List<Offer> findOffersByPaidTypes(List<Integer> paidTypes) {
        return entityManager.createQuery("SELECT distinct offers from Offer offers join OfferPaidType offerPaidTypes " +
                "on offers.offer_id = offerPaidTypes.offer.offer_id " +
                "where offerPaidTypes.paidTypeId in ?1"
        )
                .setParameter(1, paidTypes)
                .getResultList();

    }
}
