package com.supplierBHX.service;

import com.supplierBHX.dto.GRNDetailDTO;
import com.supplierBHX.entity.RatingFeedback;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.GRNDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GRNDetailService {

    @Autowired
    private GRNDetailRepository grnDetailRepository;

    public ResponseEntity<ResponseObject> findAllGRNDtailDTOByProductId(Integer productId) {
        List<GRNDetailDTO> grnDetailDTOList = new ArrayList<GRNDetailDTO>();
        grnDetailDTOList = grnDetailRepository.findAllGRNDtailDTOByProductId(productId);
        if (!grnDetailDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", grnDetailDTOList));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
