package com.supplierBHX.dto;

import com.supplierBHX.entity.GoodsReceivedNote;
import com.supplierBHX.entity.PurchaseOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GRNDetailDTO {
    private Integer id;
    private Integer productId;
    private Integer qualityProduct;
    private Integer statusProduct;
    private Double price;
    private Integer numberInPO;
    private Integer numberReceived;
    private Integer numberDamage;
    private Integer numberMissing;
    private GoodsReceivedNote goodsReceivedNote;

}
