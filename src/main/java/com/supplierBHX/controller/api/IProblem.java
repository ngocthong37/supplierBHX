package com.supplierBHX.controller.api;

import com.supplierBHX.entity.Problem;
import com.supplierBHX.entity.vm.AddProblemVM;
import com.supplierBHX.entity.vm.ProblemVM;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/problem")
public interface IProblem {
    @Operation(
            summary = "Get all problem of a supplier",
            description = "Get all problem of a supplier",
            tags = " Problem",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return Problem successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @GetMapping( value = "/allProblems" , produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProblemVM> getAllProblems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") int supplierId);


    @Operation(
            summary = "Get Problem detail by id of supplier",
            description = "Get Problem detail by id of supplier",
            tags = " Problem",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return Problem detail successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @GetMapping(value ="/getProblemDetailByIdAndSupplierId", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Problem> getProblemDetailByIdAndSupplierId(
            @RequestParam int ProblemId,
            @RequestParam int supplierId);

    @Operation(
            summary = "Create Problem for a supplier",
            description = "Create Problem for a supplier",
            tags = " Problem",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Problem successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @PostMapping(value ="/createProblem" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Problem> createProblem(@RequestBody AddProblemVM ProblemVM);

    @Operation(
            summary = "Update Problem object",
            description = "Update Problem object",
            tags = " Problem",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @PutMapping(value = "/updateProblem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Problem> updateProblem(@RequestBody Problem Problem);


    @Operation(
            summary = "ADMIN: Get list of  Problems (can be filtered)",
            description = "Get list of  Problems (can be filtered)",
            tags = " Problem",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "200", description = "Find list of  Problems successfully")
    })
    @GetMapping("/filter")
    ResponseEntity<Page<Problem>> filterProblems(
            @Parameter(description = "Input any keywords for searching the list of  Problems")
            @RequestParam(value = "accountId", required = false) String accountId,
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(name = "from", required = false)String from,
            @RequestParam(name = "to", required = false)String to,
            @ApiParam(
                    allowableValues = "NOT_CONFIRMED, RECEIVED, APPROVED, REJECTED, DELIVERING, COMPLETED",
                    example = "NOT_CONFIRMED, RECEIVED, APPROVED, REJECTED, DELIVERING, COMPLETED",
                    defaultValue = "COMPLETED",
                    type = "String")
            @RequestParam(name = "status", required = false) String status,
//            @RequestParam(name = "warehouseId", required = false) List<Integer> warehouseIds,
//            @RequestParam(name = "employeeId", required = false) List<Integer> employeeIds,
            @RequestParam(name = "supplierId", required = true) Integer supplierId,
//            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    );
}
