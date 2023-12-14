package com.supplierBHX.service;

import com.supplierBHX.Enum.PaymentInformationType;
import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.dto.InvoiceDTO;
import com.supplierBHX.dto.PaymentInformationDTO;
import com.supplierBHX.dto.PaymentInformationDTO;
import com.supplierBHX.dto.RatingProductDTO;
import com.supplierBHX.entity.Invoice;
import com.supplierBHX.entity.PaymentInformation;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.PaymentInformationRepository;
import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentInformationService {

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findAll() {
        List<PaymentInformationDTO> paymentInformationDTOS = new ArrayList<PaymentInformationDTO>();
        paymentInformationDTOS = paymentInformationRepository.findAll().stream().map(paymentInformation -> modelMapper.map(paymentInformation, PaymentInformationDTO.class)).toList();
        if (!paymentInformationDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentInformationDTOS));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> getFilteredPaymentInformations(Pageable pageable, Map<String, Object> filters) {
        Page<PaymentInformation> paymentInformationPage;

        if (filters != null && !filters.isEmpty()) {
            Map<String, Object> convertedFilters = convertFilters(filters);
            paymentInformationPage = paymentInformationRepository.findByFilters(
                    (String) convertedFilters.get("search"),
                    (List<PaymentInformationType>) convertedFilters.get("paymentInformationTypes"),
                    (LocalDate) convertedFilters.get("startDate"),
                    (LocalDate) convertedFilters.get("endDate"),
                    (Integer) convertedFilters.get("supplierId"),
                    pageable);
        } else {
            paymentInformationPage = paymentInformationRepository.findAll(pageable);
        }
        if (paymentInformationPage.hasContent()) {
            ResponseObject responseObject = new ResponseObject("OK", "Successfully", paymentInformationPage.getContent().stream().map(paymentInformation -> modelMapper.map(paymentInformation, PaymentInformationDTO.class)).toList());
            responseObject.setTotalPages(paymentInformationPage.getTotalPages()); // Set total pages
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

        if (filters.containsKey("paymentInformationType")) {
            String paymentInformationType = (String) filters.get("paymentInformationType");
            String[] paymentInformationArray = paymentInformationType.split(",");
            List<PaymentInformationType> paymentInformationTypes = convertToPaymentInformationList(paymentInformationArray);
            convertedFilters.put("paymentInformationTypes", paymentInformationTypes);
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
        String supplierIdString = (String) filters.get("supplierId");
        Integer supplierId = Integer.parseInt(supplierIdString);
        convertedFilters.put("supplierId", supplierId);

        return convertedFilters;
    }

    // Hàm chuyển đổi từ chuỗi sang mảng PaymentStatus
    public List<PaymentInformationType> convertToPaymentInformationList(String[] paymentInformationArray) {
        List<PaymentInformationType> paymentInformationTypeList = new ArrayList<>();

        for (String paymentInformation : paymentInformationArray) {
            try {
                paymentInformationTypeList.add(PaymentInformationType.valueOf(paymentInformation.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid PaymentStatus value: " + paymentInformation);
            }
        }
        return paymentInformationTypeList;
    }

    public ResponseEntity<ResponseObject> findById(Integer id) {
        Optional<PaymentInformation> paymentInformation = paymentInformationRepository.findById(id);
        if (paymentInformation.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", paymentInformation.stream().map(paymentInformation1 -> modelMapper.map(paymentInformation1, PaymentInformationDTO.class)).collect(Collectors.toList())));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
