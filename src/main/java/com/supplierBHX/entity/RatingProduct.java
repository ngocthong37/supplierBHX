package com.supplierBHX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RatingProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Float quantityScore;
    private Float qualityScore;
    private Double price;
    private Date ratingDate;
    private Boolean cooperativeState;
    private Float ratingScore;
    private Float ratingAll;
    private String feedBackFromSup;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "ratingProduct", cascade = CascadeType.ALL)
    private List<RatingImage> ratingImages;


}
