package com.example.offer_sub_system.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "offer_characteristics")
@Data
public class OfferCharacteristics {
    @EmbeddedId
    private OfferCharacteristicsId linkPk
            = new OfferCharacteristicsId();


    @Transient
    public Offer getOffer() {
        return getLinkPk().getOffer();
    }

    @Transient
    public void setCharacteristic(Characteristic characteristic) {
        getLinkPk().setCharacteristic(characteristic);
    }

    @Transient
    public void setCharacteristicValue(CharacteristicValue characteristicValue) {
        getLinkPk().setCharacteristicValue(characteristicValue);
    }
    @Transient
    public void setOffer(Offer offer) {
        getLinkPk().setOffer(offer);
    }

    @Transient
    public Characteristic getCharacteristic() {
        return getLinkPk().getCharacteristic();
    }

    @Transient
    public CharacteristicValue getCharacteristicValue() {
        return getLinkPk().getCharacteristicValue();
    }
}

@Embeddable
@Data
class OfferCharacteristicsId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic_id")
    private Characteristic characteristic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "value_id")
    private CharacteristicValue characteristicValue;
}