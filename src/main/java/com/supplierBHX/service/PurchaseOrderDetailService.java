package com.supplierBHX.service;

import com.supplierBHX.Enum.Role;
import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.PurchaseOrderDetail;
import com.supplierBHX.entity.Supplier;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
import com.supplierBHX.entity.vm.PurchaseOrderDetailVM;
import com.supplierBHX.entity.vm.UpdateNewQuantityPOD;
import com.supplierBHX.exception.ResourceNotFoundException;
import com.supplierBHX.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PurchaseOrderDetailService {
    private final PurchaseOrderRepository orderRepository;
    private final PurchaseOrderDetailRepository orderDetailRepository;

    public List<PurchaseOrderDetail> updateNewQuantities(Integer orderId, List<UpdateNewQuantityPOD> newQuantityPODS) {
        List<PurchaseOrderDetail> orderDetails = orderRepository.findById(orderId).get().getPurchaseOrderDetails();
        orderRepository.findById(orderId).ifPresent(purchaseOrder -> {
            if (purchaseOrder.getConfirmStatus() == UtilConstString.ConfirmedStatus.NOT_CONFIRMED) {
                purchaseOrder.setConfirmStatus(UtilConstString.ConfirmedStatus.RECEIVED);
                purchaseOrder.setConfirmDate(Date.from(LocalDate.now().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
            }
            purchaseOrder.setUpdatedAt(Date.from(LocalDate.now().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
            orderRepository.save(purchaseOrder);
        });
        if(! newQuantityPODS.isEmpty()) {
             orderDetails = newQuantityPODS.stream()
                    .map(updateNewQuantityPOD -> {
                        Optional<PurchaseOrderDetail> orderDetail = orderDetailRepository.findById(updateNewQuantityPOD.getId());
                        if (orderDetail.isPresent()) {
                            orderDetail.get().setNewQuantity(updateNewQuantityPOD.getNewQuantity());
                            return orderDetail.get();
                        }
                        return null;
                    })
                    .collect(Collectors.toList());
            orderDetails = orderDetailRepository.saveAll(orderDetails);
        }
        return orderDetails;
    }
}
