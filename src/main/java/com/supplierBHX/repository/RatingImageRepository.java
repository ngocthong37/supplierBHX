package com.supplierBHX.repository;

import com.supplierBHX.entity.RatingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingImageRepository extends JpaRepository<RatingImage, Integer> {
    List<RatingImage> findByRatingProductId(Integer ratingProductId);
}
