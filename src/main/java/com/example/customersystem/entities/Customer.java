package com.example.customersystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customer_id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String pass;

    @Column(unique = true)
    private String phone;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")

    private Address address;

    @ManyToMany(mappedBy = "customerList")
    @ToString.Exclude
    @JsonIgnore
    private List<PaidType> paidTypes = new ArrayList<>();

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
