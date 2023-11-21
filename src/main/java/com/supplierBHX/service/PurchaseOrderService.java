package com.supplierBHX.service;

import com.supplierBHX.entity.GoodsReceivedNote;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.PurchaseOrderDetail;
import com.supplierBHX.entity.Supplier;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
import com.supplierBHX.entity.vm.PurchaseOrderDetailVM;
import com.supplierBHX.exception.ResourceNotFoundException;
import com.supplierBHX.repository.GoodsReceivedNoteRepository;
import com.supplierBHX.repository.PurchaseOrderDetailRepository;
import com.supplierBHX.repository.PurchaseOrderRepository;
import com.supplierBHX.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository orderRepository;
    private final PurchaseOrderDetailRepository orderDetailRepository;
    private final GoodsReceivedNoteRepository goodsReceivedNoteRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public Page<PurchaseOrder> findAllBySupplierId(int supplierId, Pageable pageable)    {
        return orderRepository.findAllBySupplier_Id(supplierId, pageable);
    }

    public List<PurchaseOrder> findAll() {
        return orderRepository.findAll();
    }


    public PurchaseOrder findByIdAndSupplier_Id(Integer orderId, Integer supplierId)   {
        return orderRepository.findByIdAndSupplier_Id(orderId, supplierId).orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found"));
    }

    public AddPurchaseOrderVM addPurchaseOrder(AddPurchaseOrderVM addPurchaseOrderVM) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        // Set properties from addPurchaseOrderVM
        purchaseOrder.setTotal(addPurchaseOrderVM.getTotal());
        purchaseOrder.setDeliveryDate(addPurchaseOrderVM.getDeliveryDate());
        purchaseOrder.setReceiveDate(addPurchaseOrderVM.getReceiveDate());
        purchaseOrder.setCreatedAt(addPurchaseOrderVM.getCreatedAt());
        purchaseOrder.setDiscount(addPurchaseOrderVM.getDiscount());
        purchaseOrder.setVAT(addPurchaseOrderVM.getVAT());
        purchaseOrder.setConfirmStatus(addPurchaseOrderVM.getConfirmStatus());
        purchaseOrder.setDeliveryStatus(addPurchaseOrderVM.getDeliveryStatus());
        purchaseOrder.setWarehouseId(addPurchaseOrderVM.getWarehouseId());
        purchaseOrder.setEmployeeId(addPurchaseOrderVM.getEmployeeId());

        // Set PurchaseOrderDetails from addPurchaseOrderVM
        List<PurchaseOrderDetail> purchaseOrderDetailList = addPurchaseOrderVM.getPurchaseOrderDetails().stream()
                .map(vm -> modelMapper.map(vm, PurchaseOrderDetail.class))
                .collect(Collectors.toList());
        purchaseOrder.setPurchaseOrderDetails(purchaseOrderDetailList);

        // Set relationships
        Supplier supplier = supplierRepository.findById(addPurchaseOrderVM.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        purchaseOrder.setSupplier(supplier);

        /*GoodsReceivedNote grn = goodsReceivedNoteRepository.findById(addPurchaseOrderVM.getGoodsReceivedNoteId())
                .orElseThrow(() -> new EntityNotFoundException("GoodsReceivedNote not found"));
        purchaseOrder.setGoodsReceivedNote(grn);*/

        // Save PurchaseOrder
        PurchaseOrder savedPurchaseOrder = orderRepository.save(purchaseOrder);

        // Save PurchaseOrderDetails
        List<PurchaseOrderDetailVM> detailVMs = addPurchaseOrderVM.CalculateUnitProduct(addPurchaseOrderVM.getPurchaseOrderDetails());
        if ((long) detailVMs.size() > 0) {
            for (PurchaseOrderDetailVM detailVM : detailVMs) {
                PurchaseOrderDetail purchaseOrderDetail = getPurchaseOrderDetail(detailVM, savedPurchaseOrder);

                // Save PurchaseOrderDetail
                orderDetailRepository.save(purchaseOrderDetail);
            }
        }

        return modelMapper.map(savedPurchaseOrder, AddPurchaseOrderVM.class);
    }

    private PurchaseOrderDetail getPurchaseOrderDetail(PurchaseOrderDetailVM detailVM, PurchaseOrder savedPurchaseOrder) {
        PurchaseOrderDetail purchaseOrderDetail = modelMapper.map(detailVM, PurchaseOrderDetail.class);
        // Set relationship
        purchaseOrderDetail.setPurchaseOrder(savedPurchaseOrder);
        return purchaseOrderDetail;
    }

}
