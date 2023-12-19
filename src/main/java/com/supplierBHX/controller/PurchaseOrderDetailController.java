package com.supplierBHX.controller;

import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.controller.api.IPurchaseOrder;
import com.supplierBHX.controller.api.IPurchaseOrderDetail;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.PurchaseOrderDetail;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
import com.supplierBHX.entity.vm.UpdateNewQuantityPOD;
import com.supplierBHX.service.PurchaseOrderDetailService;
import com.supplierBHX.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseOrderDetailController implements IPurchaseOrderDetail {

    public final PurchaseOrderDetailService orderDetailService;

    @Override
    public ResponseEntity<List<PurchaseOrderDetail>> updatePurchaseOrder(Integer orderId, List<UpdateNewQuantityPOD> newQuantities) {
        List<PurchaseOrderDetail> updatePurchaseOrder = orderDetailService.updateNewQuantities(orderId, newQuantities);
        return ResponseEntity
                .status(updatePurchaseOrder != null ? HttpStatus.OK : HttpStatus.NOT_IMPLEMENTED)
                .body(updatePurchaseOrder);
    }
}
