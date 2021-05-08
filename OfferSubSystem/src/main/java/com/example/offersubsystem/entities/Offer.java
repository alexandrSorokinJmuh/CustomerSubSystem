package com.example.offersubsystem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offer")
@Data
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offer_id;

    private String name;
    private Float price;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<OfferPaidType> paidTypes = new ArrayList<>();

    public void setPaidTypes(List<OfferPaidType> paidTypes) {
        //this.sonEntities = aSet; //This will override the set that Hibernate is tracking.

        this.paidTypes.clear();
        if (paidTypes != null) {
            this.paidTypes.addAll(paidTypes);
        }
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    @JsonIgnore
    private Category category;


    @OneToMany(mappedBy = "linkPk.offer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<OfferCharacteristics> offerCharacteristics = new ArrayList<>();

    public void setOfferCharacteristics(List<OfferCharacteristics> offerCharacteristics)
    {
        this.offerCharacteristics.clear();
        if (offerCharacteristics != null) {
            this.offerCharacteristics.addAll(offerCharacteristics);
        }
    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "offer_characteristics",
//            inverseJoinColumns = @JoinColumn(name = "characteristic_id", table = "characteristics", referencedColumnName = "characteristic_id"),
//            joinColumns = @JoinColumn(name = "offer_id", table = "offers",  referencedColumnName = "offer_id")
//
//    )
//    @JsonIgnore
//    @ToString.Exclude
//    private List<Characteristic> characteristics = new ArrayList<>();
//
//
//    @ManyToMany(mappedBy = "offersList", cascade = CascadeType.DETACH)
//    @ToString.Exclude
//    @JsonIgnore
//    private List<CharacteristicValue> characteristicValues = new ArrayList<>();
}
