package com.example.offer_sub_system.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "offer_paid_type")
@Data
@IdClass(OfferPaidTypeId.class)
public class OfferPaidType {
    @Id
    @ManyToOne
    Offer offer;
    @Id
    Integer paidTypeId;

    public OfferPaidType() {}

    public OfferPaidType(Offer offer, Integer paidTypeId) {
        this.offer = offer;
        this.paidTypeId = paidTypeId;
    }
}

@Data
class OfferPaidTypeId implements Serializable{
    private Offer offer;
    private Integer paidTypeId;

    public OfferPaidTypeId(){}

    public OfferPaidTypeId(Offer offer, Integer paidTypeId) {
        this.offer = offer;
        this.paidTypeId = paidTypeId;
    }
}
