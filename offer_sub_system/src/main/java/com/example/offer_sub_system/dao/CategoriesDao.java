package com.example.offer_sub_system.dao;

import com.example.offer_sub_system.entities.Category;
import com.example.offer_sub_system.entities.Offer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CategoriesDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Category> getAll() {
        return entityManager.createQuery("from Category", Category.class).getResultList();
    }

    public Category getById(int id) {
        return entityManager.find(Category.class, id);
    }

    public Category create(Category category) {
        entityManager.persist(category);
        return category;
    }

    public Category update(int id, Category category) {
        Category original = entityManager.find(Category.class, id);
        if (original != null) {

            original.setName(category.getName());

            original.getOfferList().clear();
            for (Offer offer : category.getOfferList()) {
                original.getOfferList().add(offer);
                offer.setCategory(original);
            }

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Category category = entityManager.find(Category.class, id);

        if (category != null) {
            category.getOfferList().clear();
            entityManager.remove(category);
        }
    }
}
