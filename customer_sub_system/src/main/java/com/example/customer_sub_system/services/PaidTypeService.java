package com.example.customer_sub_system.services;

import com.example.customer_sub_system.dao.PaidTypeDao;
import com.example.customer_sub_system.dto.PaidTypeDto;
import com.example.customer_sub_system.entities.PaidType;
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

    public PaidType updateWithDto(PaidTypeDto paidTypeDto) {
        PaidType paidType = new PaidType();
        paidType.setName(paidTypeDto.getName());

        return update(paidTypeDto.getPaidTypeId(), paidType);
    }
}
