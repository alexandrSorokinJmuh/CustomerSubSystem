package com.example.customersystem.services;

import com.example.customersystem.dao.PaidTypeDao;
import com.example.customersystem.entities.PaidType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaidTypeService {

    private final PaidTypeDao paidTypeDao;

    public PaidTypeService(PaidTypeDao paidTypeDao) {
        this.paidTypeDao = paidTypeDao;
    }

    public PaidType create(PaidType paidType){
        return paidTypeDao.create(paidType);
    }

    public PaidType update(int id, PaidType paidType){
        return paidTypeDao.update(id, paidType);
    }
    public void delete(int id){
        paidTypeDao.delete(id);
    }
    public List<PaidType> getAll() {
        return paidTypeDao.getAll();
    }

    public PaidType getById(int id) {
        return paidTypeDao.getById(id);
    }
}
