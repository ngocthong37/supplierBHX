package com.supplierBHX.dto;


import com.supplierBHX.entity.ZoneDelivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private String defaultImageUrl;
    private List<ZoneDeliveryDTO> zoneDeliveries;
    private List<ProductImageUrlDTO> productImageUrlList;
    private String reasonDecline;
}
