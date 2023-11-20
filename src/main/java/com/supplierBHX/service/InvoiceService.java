package com.supplierBHX.service;

import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.dto.InvoiceDTO;
import com.supplierBHX.entity.Invoice;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.InvoiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findAll() {
        List<InvoiceDTO> invoiceDTOList = new ArrayList<InvoiceDTO>();
        invoiceDTOList = invoiceRepository.findAll().stream().map(invoice -> modelMapper.map(invoice, InvoiceDTO.class)).toList();
        if (!invoiceDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", invoiceDTOList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> getFilteredInvoices(Pageable pageable, Map<String, Object> filters) {
        Page<Invoice> invoicePage;

        if (filters != null && !filters.isEmpty()) {
            Map<String, Object> convertedFilters = convertFilters(filters);
            invoicePage = invoiceRepository.findByFilters(
                    (String) convertedFilters.get("search"),
                    (List<PaymentStatus>) convertedFilters.get("paymentStatusList"),
                    (LocalDate) convertedFilters.get("paymentDateFrom"),
                    (LocalDate) convertedFilters.get("paymentDateTo"),
                    pageable);
        } else {
            invoicePage = invoiceRepository.findAll(pageable);
        }

        return invoicePage.hasContent() ?
                ResponseEntity.ok(new ResponseObject("OK", "Successfully", invoicePage.getContent().stream().map(invoice -> modelMapper.map(invoice, InvoiceDTO.class)).toList())) :
                ResponseEntity.ok(new ResponseObject("Not found", "Not found", ""));
    }

    private Map<String, Object> convertFilters(Map<String, Object> filters) {
        Map<String, Object> convertedFilters = new HashMap<>();

        if (filters.containsKey("search")) {
            String invoiceNumber = (String) filters.get("search");
            convertedFilters.put("search", invoiceNumber);
        }

        if (filters.containsKey("paymentStatus")) {
            String paymentStatusStrings = (String) filters.get("paymentStatus");
            String[] paymentStatusArray = paymentStatusStrings.split(",");
            List<PaymentStatus> paymentStatusList = convertToPaymentStatusList(paymentStatusArray);
            convertedFilters.put("paymentStatusList", paymentStatusList);
        }

        if (filters.containsKey("paymentDateFrom")) {
            String paymentDateFromString = (String) filters.get("paymentDateFrom");
            LocalDate paymentDateFrom = LocalDate.parse(paymentDateFromString, DateTimeFormatter.ISO_DATE);
            convertedFilters.put("paymentDateFrom", paymentDateFrom);
        }

        if (filters.containsKey("paymentDateTo")) {
            String paymentDateToString = (String) filters.get("paymentDateTo");
            LocalDate paymentDateTo = LocalDate.parse(paymentDateToString, DateTimeFormatter.ISO_DATE);
            convertedFilters.put("paymentDateTo", paymentDateTo);
        }

        return convertedFilters;
    }

    // Hàm chuyển đổi từ chuỗi sang mảng PaymentStatus
    public List<PaymentStatus> convertToPaymentStatusList(String[] paymentStatusStrings) {
        List<PaymentStatus> paymentStatusList = new ArrayList<>();

        for (String paymentStatusString : paymentStatusStrings) {
            try {
                paymentStatusList.add(PaymentStatus.valueOf(paymentStatusString.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid PaymentStatus value: " + paymentStatusString);
            }
        }
        return paymentStatusList;
    }
}
