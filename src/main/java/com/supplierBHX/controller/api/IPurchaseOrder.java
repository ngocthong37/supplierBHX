package com.supplierBHX.controller.api;

import com.supplierBHX.Enum.UtilConstString;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.vm.AddPurchaseOrderVM;
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

@RequestMapping(path = "/api/v1/order")
public interface IPurchaseOrder {

    @Operation(
            summary = "Get all order of a supplier",
            description = "Get all order of a supplier",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return order successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @GetMapping( value = "/allPurchaseOrders" , produces = MediaType.APPLICATION_JSON_VALUE)
    Page<PurchaseOrder> getAllPurchaseOrdersForSupplier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") int supplierId);


    @Operation(
            summary = "Get order detail by id of supplier",
            description = "Get order detail by id of supplier",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return order detail successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @GetMapping(value ="/getOrderDetailByIdAndSupplierId", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PurchaseOrder> getOrderDetailByIdAndSupplierId(
            @RequestParam int orderId,
            @RequestParam int supplierId);


    @Operation(
            summary = "Create order for a supplier",
            description = "Create order for a supplier",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create order successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @PostMapping(value ="/admin/createOrder" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AddPurchaseOrderVM> createPurchaseOrder(@RequestBody AddPurchaseOrderVM purchaseOrderVM);

    @Operation(
            summary = "Get all orders of suppliers",
            description = "Get all orders of suppliers",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return orders successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @GetMapping(value ="/admin/allPurchaseOrders" , produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrdersForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @Operation(
            summary = "Update confirm status of an order",
            description = "",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @PatchMapping("/admin/updateConfirmStatusForOrder")
    ResponseEntity<PurchaseOrder> updateConfirmStatusForPurchaseOrder(
            @AuthenticationPrincipal String username,
            @RequestParam Integer id,
            @RequestParam
            @ApiParam(
//                    allowableValues = "NOT_CONFIRMED, RECEIVED, APPROVED, REJECTED",
//                    example = "WAITING,  PROCESSING, APPROVED, REJECTED",
//                    defaultValue = "WAITING",
                    type = "String")
            String confirmStatus);

    @Operation(
            summary = "Update order object",
            description = "Update order object",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @PutMapping(value = "/admin/updateOrder", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PurchaseOrder> updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder);


    @Operation(
            summary = "ADMIN: Get list of purchase orders (can be filtered)",
            description = "Get list of purchase orders (can be filtered)",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "200", description = "Find list of purchase orders successfully")
    })
    @GetMapping("/filter")
    ResponseEntity<Page<PurchaseOrder>> filterPurchaseOrders(
            @Parameter(description = "Input any keywords for searching the list of purchase orders")
            @RequestParam(value = "accountId", required = false) String accountId,
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @ApiParam(
                    allowableValues = "NOT_CONFIRMED, RECEIVED, APPROVED, REJECTED, DELIVERING, COMPLETED",
                    example = "NOT_CONFIRMED, RECEIVED, APPROVED, REJECTED, DELIVERING, COMPLETED",
                    defaultValue = "COMPLETED",
                    type = "String")
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "warehouseId", required = false) List<Integer> warehouseIds,
            @RequestParam(name = "employeeId", required = false) List<Integer> employeeIds,
            @RequestParam(name = "supplierId", required = false) List<Integer> supplierIds,
//            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    );

    @Operation(
            summary = "Update status of an order",
            description = "",
            tags = "Purchase Order",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @PatchMapping("/updateStatusForOrder")
    ResponseEntity<PurchaseOrder> updateStatusForOrder(
            @RequestParam Integer id,
            @RequestParam
            @ApiParam(allowableValues = "NOT_CONFIRMED, RECEIVED, DELIVERING, COMPLETED",
                    example = "NOT_CONFIRMED, RECEIVED, DELIVERING, COMPLETED",
                    defaultValue = "RECEIVED",
                    type = "String")
            String status);
}
