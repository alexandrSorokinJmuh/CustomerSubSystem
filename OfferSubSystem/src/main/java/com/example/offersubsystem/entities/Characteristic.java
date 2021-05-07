package com.example.offersubsystem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "characteristic")
@Data
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer characteristic_id;

    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany(mappedBy = "characteristics", cascade = CascadeType.DETACH)
    @ToString.Exclude
    @JsonIgnore
    private List<Offer> offersList = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "offer_characteristics",
            inverseJoinColumns = @JoinColumn(name = "value_id", table = "characteristic_values", referencedColumnName = "value_id"),
            joinColumns = @JoinColumn(name = "characteristic_id", table = "characteristics",  referencedColumnName = "characteristic_id")

    )
    @JsonIgnore
    @ToString.Exclude
    List<CharacteristicValue> characteristicValues = new ArrayList<>();

}
