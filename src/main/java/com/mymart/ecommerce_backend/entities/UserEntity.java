package com.mymart.ecommerce_backend.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Set;


@Entity
    @Setter
    @Getter
    @Table(name = "USER_DTLS")
    public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer userId;
        private String name;
        private String email;
        private String pwd;
        private Long phno;
        private String pwdUpdated;

        private String activeSw;

        @CreationTimestamp
        private LocalDate createdDate;

        @UpdateTimestamp
        private LocalDate updatedDate;

        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId"))
        private Set<RoleEntity> roles;

    }

