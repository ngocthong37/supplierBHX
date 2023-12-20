package com.supplierBHX.entity.vm;

import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.ProblemDetail;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.Supplier;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProblemVM {
    private Integer id;
    private Date dateCreated;
    private Date dateConfirmed;
    private Integer status;
    private String solution;

    @Enumerated(EnumType.STRING)
    private UtilConstString.ProblemType problemType;
    private Supplier supplier;
    private List<ProblemDetail> problemDetails;
    private PurchaseOrder purchaseOrder;
    private String createdBy;
}
