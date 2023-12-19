package com.supplierBHX.entity;

import com.supplierBHX.Enum.QualityProduct;
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
public class GRNDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private QualityProduct qualityProduct;
    private Double price;
    private Integer numberInPO;
    private Integer numberReceived;
    private Integer numberDamage;
    private Integer numberMissing;


    @ManyToOne
    @JoinColumn(name = "grn_id")
    private GoodsReceivedNote goodsReceivedNote;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
