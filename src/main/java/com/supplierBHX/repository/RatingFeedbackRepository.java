package com.supplierBHX.repository;

import com.supplierBHX.entity.RatingFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingFeedbackRepository extends JpaRepository<RatingFeedback, Integer> {
    List<RatingFeedback> findByRatingProductId(Integer ratingProductId);
}
