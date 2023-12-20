package com.supplierBHX.service;

import com.supplierBHX.entity.Problem;
import com.supplierBHX.entity.vm.AddProblemVM;
import com.supplierBHX.entity.vm.ProblemVM;
import com.supplierBHX.exception.ResourceNotFoundException;
import com.supplierBHX.repository.AccountRepository;
import com.supplierBHX.repository.ProblemRepository;
import com.supplierBHX.repository.PurchaseOrderRepository;
import com.supplierBHX.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final AccountRepository accountRepository;

    public Page<ProblemVM> findAllBySupplierId(int supplierId, Pageable pageable) {
        Page<Problem> problemPage = problemRepository.findAllBySupplier_Id(supplierId, pageable);
        List<ProblemVM> problemVMList = problemPage.getContent()
                .stream()
                .map(this::convertToProblemVM)
                .collect(Collectors.toList());

        return new PageImpl<>(problemVMList, pageable, problemPage.getTotalElements());
    }

    private ProblemVM convertToProblemVM(Problem p) {
        ProblemVM problemVM = new ProblemVM();
        problemVM.setId(p.getId());
        problemVM.setDateCreated(p.getDateCreated());
        problemVM.setDateConfirmed(p.getDateConfirmed());
        problemVM.setStatus(p.getStatus());
        problemVM.setSolution(p.getSolution());
        problemVM.setProblemType(p.getProblemType());
        problemVM.setSupplier(p.getSupplier());
        problemVM.setProblemDetails(p.getProblemDetails());
        problemVM.setPurchaseOrder(p.getPurchaseOrder());
        problemVM.setCreatedBy(accountRepository.findById(p.getEmployeeId()).get().getUsername());
        return problemVM;
    }

    public Problem findByIdAndSupplier_Id(int problemId, int supplierId) {
        return problemRepository.findByIdAndSupplier_Id(problemId, supplierId).orElseThrow(() -> new ResourceNotFoundException("Problem not found"));
    }

    public Problem addProblem(AddProblemVM problemVM) {
        Problem problem = new Problem();
        problem.setDateCreated(Date.from(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        problem.setStatus(1);
        problem.setEmployeeId(problemVM.getEmployeeId());
        problem.setSolution(problemVM.getSolution());
        problem.setProblemType(problemVM.getProblemType());
        problem.setSupplier(supplierRepository.findById(problemVM.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found")));
        problem.setProblemDetails(problemVM.getProblemDetails());
        problem.setPurchaseOrder(purchaseOrderRepository.findById(problemVM.getPurchaseOrderId()).orElseThrow(() -> new ResourceNotFoundException("Purchase order not found")));
        return problemRepository.save(problem);
    }
    public Problem updateProblem(Problem problem) {
        Problem problem1 = problemRepository.findById(problem.getId()).orElseThrow(() -> new ResourceNotFoundException("Problem not found"));
        problem1.setSolution(problem.getSolution());
        problem1.setStatus(problem.getStatus());
        problem1.setProblemType(problem.getProblemType());
        problem1.setProblemDetails(problem.getProblemDetails());
        return problemRepository.save(problem1);
    }
}
