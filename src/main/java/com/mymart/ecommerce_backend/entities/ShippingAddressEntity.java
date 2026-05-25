package com.mymart.ecommerce_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SHIPPING_ADDRESS")
@Getter
@Setter
public class ShippingAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addr_id")
    private Integer addrId;

    @Column(name = "house_num", length = 100)
    private String houseNum;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "zipcode", length = 20)
    private String zipcode;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "addr_type", length = 500)
    private String addrType;

    private String activeSw;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
