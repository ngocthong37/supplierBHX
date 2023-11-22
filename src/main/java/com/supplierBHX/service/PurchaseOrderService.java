package com.supplierBHX.service;

import com.supplierBHX.Enum.AccountType;
import com.supplierBHX.Enum.Role;
import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.entity.*;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
import com.supplierBHX.entity.vm.PurchaseOrderDetailVM;
import com.supplierBHX.exception.ResourceNotFoundException;
import com.supplierBHX.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public Page<PurchaseOrder> findAllBySupplierId(int supplierId, Pageable pageable) {
        return orderRepository.findAllBySupplier_Id(supplierId, pageable);
    }

    public List<PurchaseOrder> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).toList();
    }


    public PurchaseOrder findByIdAndSupplier_Id(Integer orderId, Integer supplierId) {
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

    public PurchaseOrder UpdatePurchaseOrderConfirmStatus(String username, Integer id, String confirmStatus) {
        Optional<Account> account = accountRepository.findByUserName(username);
        Optional<PurchaseOrder> purchaseOrderOptional = orderRepository.findById(id);
        if (purchaseOrderOptional.isPresent() && account.isPresent() && account.get().getRole() == Role.ADMIN) {
            PurchaseOrder updatePurchaseOrder = purchaseOrderOptional.get();
            updatePurchaseOrder.setConfirmStatus(UtilConstString.ConfirmedStatus.valueOf(confirmStatus));
            updatePurchaseOrder.setConfirmDate(Date.from(new Date().toInstant()));
            updatePurchaseOrder.setEmployeeId(account.get().getId());
            return orderRepository.save(updatePurchaseOrder);
        }
        return null;
    }

    public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        Optional<PurchaseOrder> purchaseOrderOptional = orderRepository.findById(purchaseOrder.getId());
        if (purchaseOrderOptional.isPresent()) {
            PurchaseOrder updatePurchaseOrder = purchaseOrderOptional.get();
            updatePurchaseOrder.setConfirmStatus(purchaseOrder.getConfirmStatus());
            updatePurchaseOrder.setConfirmDate(purchaseOrder.getConfirmDate());
            updatePurchaseOrder.setDeliveryDate(purchaseOrder.getDeliveryDate());
            updatePurchaseOrder.setDeliveryStatus(purchaseOrder.getDeliveryStatus());
            updatePurchaseOrder.setDiscount(purchaseOrder.getDiscount());
            updatePurchaseOrder.setEmployeeId(purchaseOrder.getEmployeeId());
            updatePurchaseOrder.setReceiveDate(purchaseOrder.getReceiveDate());
            updatePurchaseOrder.setTotal(purchaseOrder.getTotal());
            updatePurchaseOrder.setVAT(purchaseOrder.getVAT());
            updatePurchaseOrder.setWarehouseId(purchaseOrder.getWarehouseId());
            return orderRepository.save(updatePurchaseOrder);
        }
        return null;
    }

    public Page<PurchaseOrder> filterPurchaseOrders(
            String accountId,
            List<String> keywords,
            LocalDate from,
            LocalDate to,
            List<String> confirmStatuses,
            List<String> deliveryStatuses,
            List<Integer> warehouseIds,
            List<Integer> employeeIds,
            List<Integer> supplierIds,
            String sort,
            Pageable pageable) {
        List<PurchaseOrder> purchaseOrders = orderRepository.findAll();
        if (keywords != null && !keywords.isEmpty()){
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> keywords
                            .stream()
                            .anyMatch(k -> c.getSupplier().getSupplierName().toLowerCase().contains(k.toLowerCase()) ||
                                    c.getSupplier().getAddress().toLowerCase().contains(k.toLowerCase()) ||
                                    c.getSupplier().getPhoneNumber().toLowerCase().contains(k.toLowerCase()) ||
                                    c.getDeliveryStatus().toString().toLowerCase().contains(k.toLowerCase()) ||
                                    c.getConfirmStatus().toString().toLowerCase().contains(k.toLowerCase())
                            )
                    ).toList();
        }
        if (from != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> c.getCreatedAt().after(java.sql.Date.valueOf(from)))
                    .toList();
        }
        if (to != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> c.getCreatedAt().before(java.sql.Date.valueOf(to)))
                    .toList();
        }
        if (confirmStatuses != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> confirmStatuses
                            .stream()
                            .anyMatch(k -> c.getConfirmStatus().toString().toLowerCase().contains(k.toLowerCase()))
                    ).toList();
        }
        if (deliveryStatuses != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> deliveryStatuses
                            .stream()
                            .anyMatch(k -> c.getDeliveryStatus().toString().toLowerCase().contains(k.toLowerCase()))
                    ).toList();
        }
        if (warehouseIds != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> warehouseIds
                            .stream()
                            .allMatch(k -> c.getWarehouseId().equals(k))
                    ).toList();
        }
        if (employeeIds != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> employeeIds
                            .stream()
                            .allMatch(k -> c.getEmployeeId().equals(k))
                    ).toList();
        }
        if (supplierIds != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .filter(c -> supplierIds
                            .stream()
                            .allMatch(k -> c.getSupplier().getId().equals(k))
                    ).toList();
        }
        /*if (sort != null) {
            purchaseOrders = purchaseOrders
                    .stream()
                    .sorted((c1, c2) -> {
                        if (sort.equals("confirmStatus")) {
                            return c1.getConfirmStatus().compareTo(c2.getConfirmStatus());
                        }
                        if (sort.equals("deliveryStatus")) {
                            return c1.getDeliveryStatus().compareTo(c2.getDeliveryStatus());
                        }
                        if (sort.equals("createdAt")) {
                            return c1.getCreatedAt().compareTo(c2.getCreatedAt());
                        }
                        if (sort.equals("total")) {
                            return c1.getTotal().compareTo(c2.getTotal());
                        }
                        if (sort.equals("discount")) {
                            return c1.getDiscount().compareTo(c2.getDiscount());
                        }
                        if (sort.equals("VAT")) {
                            return c1.getVAT().compareTo(c2.getVAT());
                        }
                        if (sort.equals("receiveDate")) {
                            return c1.getReceiveDate().compareTo(c2.getReceiveDate());
                        }
                        if (sort.equals("deliveryDate")) {
                            return c1.getDeliveryDate().compareTo(c2.getDeliveryDate());
                        }
                        if (sort.equals("confirmDate")) {
                            return c1.getConfirmDate().compareTo(c2.getConfirmDate());
                        }
                        if (sort.equals("warehouseId")) {
                            return c1.getWarehouseId().compareTo(c2.getWarehouseId());
                        }
                        if (sort.equals("employeeId")) {
                            return c1.getEmployeeId().compareTo(c2.getEmployeeId());
                        }
                        if (sort.equals("supplierId")) {
                            return c1.getSupplier().getId().compareTo(c2.getSupplier().getId());
                        }
                        return 0;
                    })
                    .toList();
        }*/
        Page<PurchaseOrder> purchaseOrderPage = purchaseOrders.stream().collect(Collectors.collectingAndThen(Collectors.toList(), page -> new PageImpl(page, pageable, page.size())));
        return purchaseOrderPage;
    }
}
