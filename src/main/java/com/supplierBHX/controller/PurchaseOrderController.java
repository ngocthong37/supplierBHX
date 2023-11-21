package com.supplierBHX.controller;

import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
import com.supplierBHX.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/order")
@RequiredArgsConstructor
public class PurchaseOrderController {

    public final PurchaseOrderService orderService;

    @GetMapping("/allPurchaseOrders")
    public Page<PurchaseOrder> getAllPurchaseOrdersForSupplier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") int supplierId) {

        Pageable pageable = PageRequest.of(page, size);

        return orderService.findAllBySupplierId(supplierId, pageable);
    }

    @GetMapping("/allPurchaseOrdersForAdmin")
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrdersForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<PurchaseOrder> purchaseOrderPage = orderService.findAll();
        return ResponseEntity
                .status(!purchaseOrderPage.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(purchaseOrderPage);
    }

    @GetMapping("/getOrderDetailByIdAndSupplierId")
    public ResponseEntity<PurchaseOrder> getOrderDetailByIdAndSupplierId(
            @RequestParam int orderId,
            @RequestParam int supplierId) {
        PurchaseOrder dto = orderService.findByIdAndSupplier_Id(orderId, supplierId);
        return ResponseEntity
                .status(dto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(dto);
    }

    @PostMapping("/createOrderForAdmin")
    public ResponseEntity<AddPurchaseOrderVM> createPurchaseOrder(@RequestBody AddPurchaseOrderVM purchaseOrderVM) {
        AddPurchaseOrderVM addPurchaseOrder = orderService.addPurchaseOrder(purchaseOrderVM);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addPurchaseOrder);
    }
}
