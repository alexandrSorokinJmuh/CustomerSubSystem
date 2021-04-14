package com.example.customersystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paid_type")
@Data
public class PaidType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Integer paid_type_id;

    private String name;



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "customer_paid_type",
            inverseJoinColumns = @JoinColumn(name = "customer_id", table = "customer", referencedColumnName = "customer_id"),
            joinColumns = @JoinColumn(name = "paid_type_id", table = "paid_type",  referencedColumnName = "paid_type_id")
    )
    @JsonIgnore
    @ToString.Exclude
    List<Customer> customerList = new ArrayList<>();
}
