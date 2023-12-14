package com.supplierBHX.service;

import com.supplierBHX.Enum.PaymentInformationType;
import com.supplierBHX.dto.InvoiceDTO;
import com.supplierBHX.dto.PaymentInformationDTO;
import com.supplierBHX.dto.RatingProductDTO;
import com.supplierBHX.entity.PaymentInformation;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
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
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", ratingProductList.stream().map(ratingProduct -> modelMapper.map(ratingProduct, RatingProductDTO.class)).toList()));
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
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }

    }

    public ResponseEntity<ResponseObject> getFilteredRatingFeedback(Pageable pageable, Map<String, Object> filters) {
        Page<RatingProduct> ratingProductPage;

        if (filters != null && !filters.isEmpty()) {
            Map<String, Object> convertedFilters = convertFilters(filters);
            ratingProductPage = ratingProductRepository.findByFilters(
                    (String) convertedFilters.get("search"),
                    (LocalDate) convertedFilters.get("startDate"),
                    (LocalDate) convertedFilters.get("endDate"),
                    (Integer) convertedFilters.get("supplierId"),
                    (Float) convertedFilters.get("minRatingScore"),
                    (Float) convertedFilters.get("maxRatingScore"),
                    pageable);
        } else {
            ratingProductPage = ratingProductRepository.findAll(pageable);
        }

        if (ratingProductPage.hasContent()) {
            ResponseObject responseObject = new ResponseObject("OK", "Successfully", ratingProductPage.getContent().stream().map(ratingProduct -> modelMapper.map(ratingProduct, RatingProductDTO.class)).toList());
            responseObject.setTotalPages(ratingProductPage.getTotalPages()); // Set total pages
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
    private Map<String, Object> convertFilters(Map<String, Object> filters) {
        Map<String, Object> convertedFilters = new HashMap<>();
        if (filters.containsKey("search")) {
            String paymentInformationNumber = (String) filters.get("search");
            convertedFilters.put("search", paymentInformationNumber);
        }

        if (filters.containsKey("startDate")) {
            String paymentDateFromString = (String) filters.get("startDate");
            LocalDate paymentDateFrom = LocalDate.parse(paymentDateFromString, DateTimeFormatter.ISO_DATE);
            convertedFilters.put("startDate", paymentDateFrom);
        }

        if (filters.containsKey("endDate")) {
            String paymentDateToString = (String) filters.get("endDate");
            LocalDate paymentDateTo = LocalDate.parse(paymentDateToString, DateTimeFormatter.ISO_DATE);
            convertedFilters.put("endDate", paymentDateTo);
        }
        if (filters.containsKey("minRatingScore")) {
            String minRatingScoreString = (String) filters.get("minRatingScore");
            Float minRatingScore = Float.parseFloat(minRatingScoreString);
            convertedFilters.put("minRatingScore", minRatingScore);
        }
        if (filters.containsKey("maxRatingScore")) {
            String maxRatingScoreString = (String) filters.get("maxRatingScore");
            Float maxRatingScore = Float.parseFloat(maxRatingScoreString);
            convertedFilters.put("maxRatingScore", maxRatingScore);
        }
        String supplierIdString = (String) filters.get("supplierId");
        Integer supplierId = Integer.parseInt(supplierIdString);
        convertedFilters.put("supplierId", supplierId);

        return convertedFilters;
    }

}
