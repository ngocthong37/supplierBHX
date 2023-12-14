package com.supplierBHX.service;

import com.supplierBHX.dto.InvoiceDetailDTO;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.InvoiceDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceDetailService {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findByInvoice(Integer invoiceId) {
        List<InvoiceDetailDTO> invoiceDetailDTOList = new ArrayList<InvoiceDetailDTO>();
        invoiceDetailDTOList = invoiceDetailRepository.findByInvoiceId(invoiceId).stream().map(invoiceDetail -> modelMapper.map(invoiceDetail, InvoiceDetailDTO.class)).toList();
        if (!invoiceDetailDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", invoiceDetailDTOList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
