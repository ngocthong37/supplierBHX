package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;
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
    private Integer priceScore;
    private Integer quantityScore;
    private Integer qualityScore;
    private Integer deliverScore;
    private LocalDate ratingDate;
    private Float ratingScore;
    private Double totalOrderQuantity;
    private Double totalDeliveredQuantity;
    private Double totalReceivedQuantity;
    private Integer createId;

    @JsonBackReference(value = "product-ratingProduct")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference(value = "supplier-ratingProduct")
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @JsonManagedReference(value = "ratingProduct-ratingFeedback")
    @JsonIgnore
    @OneToMany(mappedBy = "ratingProduct")
    private List<RatingFeedback> ratingFeedbacks;

    @JsonManagedReference(value = "ratingProduct-ratingImage")
    @JsonIgnore
    @OneToMany(mappedBy = "ratingProduct")
    private List<RatingImage> ratingImages;
}
