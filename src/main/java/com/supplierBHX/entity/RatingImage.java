package com.supplierBHX.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class RatingImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String image;

    @JsonBackReference(value = "ratingProduct-ratingImage")
    @ManyToOne
    @JoinColumn(name = "rating_product_id")
    private RatingProduct ratingProduct;

}
