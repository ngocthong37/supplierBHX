package com.supplierBHX.service;

import com.supplierBHX.dto.GRNDetailDTO;
import com.supplierBHX.entity.GRNDetail;
import com.supplierBHX.entity.RatingFeedback;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.repository.GRNDetailRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ResponseObject> findAllGRNDetailByProductId(Integer productId) {
        List<GRNDetail> grnDetailList = new ArrayList<GRNDetail>();
        grnDetailList = grnDetailRepository.findAllGRNDetailByProductId(productId);
        if (!grnDetailList.isEmpty()) {

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", grnDetailList.stream().map(grnDetail -> modelMapper.map(grnDetail, GRNDetailDTO.class)).toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
