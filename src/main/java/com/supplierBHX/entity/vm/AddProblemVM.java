package com.supplierBHX.entity.vm;

import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.entity.ProblemDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddProblemVM {
    private Integer supplierId;
    private Integer employeeId;
    private Integer purchaseOrderId;
    private UtilConstString.ProblemType problemType;
    private String solution;
    private String description;
    private List<ProblemDetail> problemDetails;
}
