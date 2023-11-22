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
public class SupplyCapacityDTO {
    private Integer id;
    private LocalDate beginDate;
    private LocalDate endDate;
    private LocalDate dateConfirmed;
    private Integer employeeId;
    private Double number;
    private Integer productId;
    private Integer accountId;
    private Integer supplierId;
    private String status;
}

