package com.example.offersubsystem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "characteristic_value")
@Data
public class CharacteristicValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer value_id;


    private String value;


    @ManyToMany(mappedBy = "characteristicValues", cascade = CascadeType.DETACH)
    @ToString.Exclude
    @JsonIgnore
    private List<Characteristic> characteristics = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "offer_characteristics",
            inverseJoinColumns = @JoinColumn(name = "offer_id", table = "offers", referencedColumnName = "offer_id"),
            joinColumns = @JoinColumn(name = "value_id", table = "characteristic_values",  referencedColumnName = "value_id")

    )
    @JsonIgnore
    @ToString.Exclude
    List<Offer> offersList = new ArrayList<>();

}
