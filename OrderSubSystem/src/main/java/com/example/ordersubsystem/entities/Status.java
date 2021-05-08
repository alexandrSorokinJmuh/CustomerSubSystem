package com.example.ordersubsystem.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public enum Status {

    WAIT_FOR_PAID,
    SHIPPED,
    DELIVERED

}
