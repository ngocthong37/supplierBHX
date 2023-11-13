package com.supplierBHX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String supplierName;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String dateOfBirth;
    private Boolean sex;
    private String email;


}
