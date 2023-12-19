package com.supplierBHX.controller.api;

import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.PurchaseOrderDetail;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
import com.supplierBHX.entity.vm.UpdateNewQuantityPOD;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping(path = "/api/v1/orderDetail")
public interface IPurchaseOrderDetail {
    @Operation(
            summary = "Update list of new quantities by ids",
            description = "Update list of new quantities by ids",
            tags = "Purchase Order Detail",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @PutMapping(value = "/updateNewQuantity", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<PurchaseOrderDetail>> updatePurchaseOrder( @RequestParam Integer orderId, @RequestBody List<UpdateNewQuantityPOD> newQuantities);
}
