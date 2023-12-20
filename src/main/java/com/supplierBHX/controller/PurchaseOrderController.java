package com.supplierBHX.controller;

import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.controller.api.IPurchaseOrder;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
import com.supplierBHX.service.PurchaseOrderService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseOrderController implements IPurchaseOrder {

    public final PurchaseOrderService orderService;

    @Override
    public Page<PurchaseOrder> getAllPurchaseOrdersForSupplier(
           int page,
            int size,
            int supplierId) {

        Pageable pageable = PageRequest.of(page, size);

        return orderService.findAllBySupplierId(supplierId, pageable);
    }

    @Override
    public ResponseEntity<PurchaseOrder> getOrderDetailByIdAndSupplierId(
            int orderId,
            int supplierId) {
        PurchaseOrder dto = orderService.findByIdAndSupplier_Id(orderId, supplierId);
        return ResponseEntity
                .status(dto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(dto);
    }

    @Override
    public ResponseEntity<AddPurchaseOrderVM> createPurchaseOrder(AddPurchaseOrderVM purchaseOrderVM) {
        AddPurchaseOrderVM addPurchaseOrder = orderService.addPurchaseOrder(purchaseOrderVM);
        return ResponseEntity
                .status(addPurchaseOrder != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(addPurchaseOrder);
    }

    @Override
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrdersForAdmin(
           int page,
           int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<PurchaseOrder> purchaseOrderPage = orderService.findAll(pageable);
        return ResponseEntity
                .status(!purchaseOrderPage.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(purchaseOrderPage);
    }

    @Override
    public ResponseEntity<PurchaseOrder> updateConfirmStatusForPurchaseOrder(
            String username,
            Integer id,
            String confirmStatus) {
        PurchaseOrder updatePurchaseOrder = orderService.UpdatePurchaseOrderConfirmStatus(username, id, confirmStatus);
        return ResponseEntity
                .status(updatePurchaseOrder != null ? HttpStatus.OK : HttpStatus.NOT_IMPLEMENTED)
                .body(updatePurchaseOrder);
    }

    @Override
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrder updatePurchaseOrder = orderService.updatePurchaseOrder(purchaseOrder);
        return ResponseEntity
                .status(updatePurchaseOrder != null ? HttpStatus.OK : HttpStatus.NOT_IMPLEMENTED)
                .body(updatePurchaseOrder);
    }

    @Override
    public ResponseEntity<Page<PurchaseOrder>> filterPurchaseOrders(
            String accountId,
            String keywords,
            String from,
            String to,
            String status,
//            List<Integer> warehouseIds,
//            List<Integer> employeeIds,
            Integer supplierId,
            // String sort,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PurchaseOrder> filteredPurchaseOrders = orderService.filterPurchaseOrders( accountId,
                keywords, from, to, status
                ,supplierId , pageable
        );
        return ResponseEntity
                .status(filteredPurchaseOrders.stream().findAny().isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(filteredPurchaseOrders);
    }

    @Override
    public ResponseEntity<PurchaseOrder> updateStatusForOrder(Integer id, String status) {
        PurchaseOrder updatePurchaseOrder = orderService.updateStatusForOrder(id, status);
        return ResponseEntity
                .status(updatePurchaseOrder != null ? HttpStatus.OK : HttpStatus.NOT_IMPLEMENTED)
                .body(updatePurchaseOrder);
    }
}
