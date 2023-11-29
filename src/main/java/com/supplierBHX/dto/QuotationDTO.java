package com.supplierBHX.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuotationDTO {
    private Integer id;
    private Integer accountId;
    private Integer supplierId;
    private String productName;
    private Double price;
    private Double number;
    private Integer employeeId;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String unitType;
    private String status;
    private String createdAt;
    private String description;
    private LocalDate dateConfirmed;
}
