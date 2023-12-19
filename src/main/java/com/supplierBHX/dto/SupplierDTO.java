package com.supplierBHX.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SupplierDTO {

    private Integer id;
    private String supplierName;
    private String address;
    private String phoneNumber;
    private String dateOfBirth;
    private Boolean sex;
    private String email;
}
