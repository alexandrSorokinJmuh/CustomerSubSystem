package com.example.offersubsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
