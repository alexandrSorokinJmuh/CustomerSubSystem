package com.example.customer_sub_system.entities;

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
    private String password;

    @Column(unique = true)
    private String phone;

    @Column(name = "status")
    private Status status;

    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")

    private Address address;

    @ManyToMany(mappedBy = "customerList", cascade = CascadeType.DETACH)
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
                ", pass='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


}
