package com.supplierBHX.service;

import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.entity.Invoice;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public ResponseEntity<ResponseObject> findAll() {
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        invoiceList = invoiceRepository.findAll();
        if (!invoiceList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", invoiceList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> getFilteredInvoices(Pageable pageable, Map<String, Object> filters) {
        Page<Invoice> invoicePage ;

        if (filters != null && !filters.isEmpty()) {
            convertFilters(filters);
            System.out.println(filters);
            invoicePage = invoiceRepository.findByFilters((List<PaymentStatus>) filters.get("paymentStatusList"), (LocalDate) filters.get("paymentDateFrom"), (LocalDate) filters.get("paymentDateTo"), pageable);
        } else {
            invoicePage = invoiceRepository.findAll(pageable);
        }

        if (invoicePage.hasContent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", invoicePage.getContent()));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    private void convertFilters(Map<String, Object> filters) {
        if (filters.containsKey("paymentStatus")) {
            String paymentStatusStrings = (String) filters.get("paymentStatus");
            String[] paymentStatusArray = paymentStatusStrings.split(",");
            List<PaymentStatus> paymentStatusList = this.convertToPaymentStatusList(paymentStatusArray);
            filters.put("paymentStatus", paymentStatusList);
        }

        if (filters.containsKey("paymentDateFrom")) {
            String paymentDateFromString = (String) filters.get("paymentDateFrom");
            LocalDate paymentDateFrom = LocalDate.parse(paymentDateFromString);
            filters.put("paymentDateFrom", paymentDateFrom);
        }

        if (filters.containsKey("paymentDateTo")) {
            String paymentDateToString = (String) filters.get("paymentDateTo");
            LocalDate paymentDateTo = LocalDate.parse(paymentDateToString);
            filters.put("paymentDateTo", paymentDateTo);
        }
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
