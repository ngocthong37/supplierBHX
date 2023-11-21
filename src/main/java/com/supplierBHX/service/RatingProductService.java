package com.supplierBHX.service;

import com.supplierBHX.dto.InvoiceDTO;
import com.supplierBHX.dto.RatingProductDTO;
import com.supplierBHX.entity.PaymentResponse;
import com.supplierBHX.entity.RatingProduct;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.RatingProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingProductService {

    @Autowired
    private RatingProductRepository ratingProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findAll() {
        List<RatingProduct> ratingProductList = new ArrayList<RatingProduct>();
        ratingProductList = ratingProductRepository.findAll();
        if (!ratingProductList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingProductList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findByDateRangeAndSort(LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingDate").descending());
        Page<RatingProduct> ratingProductPage = ratingProductRepository.findByRatingDateBetweenOrderByRatingDate(startDate, endDate, pageable);

        if (ratingProductPage.hasContent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingProductPage.getContent().stream().map(ratingProduct -> modelMapper.map(ratingProduct, RatingProductDTO.class)).toList()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findByProductNameAndDateRange(
            String productName, LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingDate").descending());
        Page<RatingProduct> ratingProductPage = ratingProductRepository
                .findByProduct_NameContainingIgnoreCaseAndRatingDateBetweenOrderByRatingDate(
                        productName, startDate, endDate, pageable);

        if (ratingProductPage.hasContent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingProductPage.getContent().stream().map(ratingProduct -> modelMapper.map(ratingProduct, RatingProductDTO.class)).toList()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findById(Integer id) {
        Optional<RatingProduct> ratingProduct = ratingProductRepository.findById(id);
        if (ratingProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingProduct.stream().map(ratingProduct1 -> modelMapper.map(ratingProduct1, RatingProductDTO.class)).collect(Collectors.toList())));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Not found", "Not found", ""));
        }

    }
}
