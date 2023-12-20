package com.supplierBHX.controller;

import com.supplierBHX.controller.api.IProblem;
import com.supplierBHX.entity.Problem;
import com.supplierBHX.entity.vm.AddProblemVM;
import com.supplierBHX.entity.vm.ProblemVM;
import com.supplierBHX.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemController implements IProblem {


    public final ProblemService problemService;

    @Override
    public Page<ProblemVM> getAllProblems(int page, int size, int supplierId) {
        Pageable pageable = PageRequest.of(page, size);
        return problemService.findAllBySupplierId(supplierId, pageable);
    }

    @Override
    public ResponseEntity<Problem> getProblemDetailByIdAndSupplierId(int ProblemId, int supplierId) {
        Problem dto = problemService.findByIdAndSupplier_Id(ProblemId, supplierId);
        return ResponseEntity
                .status(dto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(dto);
    }

    @Override
    public ResponseEntity<Problem> createProblem(AddProblemVM ProblemVM) {
        Problem addProblemVM = problemService.addProblem(ProblemVM);
        return ResponseEntity
                .status(addProblemVM != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(addProblemVM);
    }

    @Override
    public ResponseEntity<Problem> updateProblem(Problem Problem) {
        Problem updateProblem = problemService.updateProblem(Problem);
        return ResponseEntity
                .status(updateProblem != null ? HttpStatus.OK : HttpStatus.NOT_IMPLEMENTED)
                .body(updateProblem);
    }

    @Override
    public ResponseEntity<Page<Problem>> filterProblems(String accountId, String keywords, String from, String to, String status, Integer supplierId, int page, int size) {
        return null;
    }

}
