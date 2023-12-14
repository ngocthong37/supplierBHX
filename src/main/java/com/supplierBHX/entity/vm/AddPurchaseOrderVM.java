package com.supplierBHX.entity.vm;

import com.supplierBHX.Enum.UnitType;
import com.supplierBHX.Enum.UtilConstString;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AddPurchaseOrderVM {


    private Date deliveryDate;
    private Date receiveDate;
    private Date createdAt = Date.from(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
    private Double discount;
    private Double VAT = 0.1;

    @Enumerated(EnumType.STRING)
    private UtilConstString.ConfirmedStatus confirmStatus = UtilConstString.ConfirmedStatus.WAITING;

    @Enumerated(EnumType.STRING)
    private UtilConstString.DeliveryStatus deliveryStatus = UtilConstString.DeliveryStatus.PENDING;

    private Integer warehouseId;
    private Integer employeeId;
    private Integer supplierId;
    private Double total;

    private List<PurchaseOrderDetailVM> purchaseOrderDetails;


    // Tinh gia tri cuoi cung cua 1 don hang
    public double getTotal() {
        if (purchaseOrderDetails.isEmpty()) {
            return 0;
        }
        purchaseOrderDetails = CalculateUnitProduct(purchaseOrderDetails);
        double totalProductPrice = purchaseOrderDetails.stream()
                .mapToDouble(PurchaseOrderDetailVM::getFinalAmount)
                .sum();
        if(discount > 0){
            totalProductPrice = totalProductPrice - (totalProductPrice * discount);
        }
        double orderVATAmount = totalProductPrice * VAT;
        return totalProductPrice + orderVATAmount;
    }

    // Tinh gia tri cuoi cung cua 1 san pham
    public List<PurchaseOrderDetailVM> CalculateUnitProduct(List<PurchaseOrderDetailVM> order) {
        if (order != null) {
            order.forEach(item -> item.setFinalAmount(ThanhTien(item)));
        }
        return order;
    }

    private double ThanhTien(PurchaseOrderDetailVM item) {
        double quantity = item.getQuantity() != 0 ? item.getQuantity() : item.getMass();
        double amount = item.getUnitPrice() * quantity;
        if (item.getDiscount() > 0) {
            amount = amount - (amount * item.getDiscount());
        }
        double amountVAT = item.getVAT().compareTo(0.0) > 0 ? item.getVAT() * amount : 0;
        return amount - amountVAT;
    }

}
