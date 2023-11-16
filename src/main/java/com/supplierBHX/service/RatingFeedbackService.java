package com.supplierBHX.service;

import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.RatingFeedback;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.PaymentResponseRepository;
import com.supplierBHX.repository.RatingFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingFeedbackService {

    @Autowired
    private RatingFeedbackRepository ratingFeedbackRepository;

    public ResponseEntity<ResponseObject> findAll() {
        List<RatingFeedback> ratingFeedbackList = new ArrayList<RatingFeedback>();
        ratingFeedbackList = ratingFeedbackRepository.findAll();
        if (!ratingFeedbackList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingFeedbackList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findById(Integer id) {
        Optional<RatingFeedback> ratingFeedback = ratingFeedbackRepository.findById(id);
        if (ratingFeedback.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingFeedback));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }

    }


    public Object insert(RatingFeedback ratingFeedback) {
        ratingFeedback.setCreatedAt(LocalDate.now());
        return ratingFeedbackRepository.save(ratingFeedback);
    }
}
