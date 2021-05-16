package com.example.customer_sub_system.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer address_id;

    private String city;
    private String country;
    private String state;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Customer> customerList = new ArrayList<>();


}
