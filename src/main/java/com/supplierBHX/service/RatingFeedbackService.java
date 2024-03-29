package com.supplierBHX.service;

import com.supplierBHX.Enum.ResponseStatus;
import com.supplierBHX.dto.RatingFeedbackDTO;
import com.supplierBHX.dto.RatingImageDTO;
import com.supplierBHX.dto.RatingProductDTO;
import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.RatingFeedback;
import com.supplierBHX.entity.RatingImage;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.PaymentResponseRepository;
import com.supplierBHX.repository.RatingFeedbackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingFeedbackService {

    @Autowired
    private RatingFeedbackRepository ratingFeedbackRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findAll() {
        List<RatingFeedback> ratingFeedbackList = new ArrayList<RatingFeedback>();
        ratingFeedbackList = ratingFeedbackRepository.findAll();
        if (!ratingFeedbackList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingFeedbackList.stream().map(ratingFeedback -> modelMapper.map(ratingFeedback, RatingFeedbackDTO.class)).toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findById(Integer id) {
        Optional<RatingFeedback> ratingFeedback = ratingFeedbackRepository.findById(id);
        if (ratingFeedback.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingFeedback.stream().map(ratingFeedback1 -> modelMapper.map(ratingFeedback1, RatingFeedbackDTO.class)).collect(Collectors.toList())));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }

    }


    public Object insert(RatingFeedback ratingFeedback) {
        ratingFeedback.setCreatedAt(LocalDate.now());
        ratingFeedback.setResponseStatus(ResponseStatus.CONSIDER);
        return ratingFeedbackRepository.save(ratingFeedback);
    }

    public ResponseEntity<ResponseObject> findByRatingProductId(Integer ratingProductId) {
        List<RatingFeedback> ratingFeedbackList = new ArrayList<>();
        ratingFeedbackList = ratingFeedbackRepository.findByRatingProductId(ratingProductId);
        if(!ratingFeedbackList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingFeedbackList.stream().map(ratingFeedback -> modelMapper.map(ratingFeedback, RatingFeedbackDTO.class)).toList()));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
