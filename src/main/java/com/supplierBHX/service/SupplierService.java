package com.supplierBHX.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplierBHX.Enum.UnitType;
import com.supplierBHX.entity.Quotation;
import com.supplierBHX.entity.ResponseObject;
import com.supplierBHX.entity.Supplier;
import com.supplierBHX.entity.SupplyCapacity;
import com.supplierBHX.repository.QuotationRepository;
import com.supplierBHX.repository.SupplierRepository;
import com.supplierBHX.repository.SupplyCapacityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierInterface;
    
    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private SupplyCapacityRepository supplyCapacityRepository;

    public ResponseEntity<Object> createQuotation(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (json == null || json.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Empty JSON", ""));
            }
            JsonNode jsonObjectQuotation = objectMapper.readTree(json);
            Integer productId = jsonObjectQuotation.get("productId") != null ?
                    jsonObjectQuotation.get("productId").asInt() : 1;
            Integer supplierId = jsonObjectQuotation.get("supplierId") != null ?
                    jsonObjectQuotation.get("supplierId").asInt() : 1;
            Double number = jsonObjectQuotation.get("number") != null ?
                    jsonObjectQuotation.get("number").asDouble() : 1;
            Double mass = jsonObjectQuotation.get("mass") != null ?
                    jsonObjectQuotation.get("mass").asDouble() : 1;
            String unit = jsonObjectQuotation.get("unit") != null ?
                    jsonObjectQuotation.get("unit").asText() : "";
            String beginDate = jsonObjectQuotation.get("beginDate") != null ?
                    jsonObjectQuotation.get("beginDate").asText() : "";
            String endDate = jsonObjectQuotation.get("endDate") != null ?
                    jsonObjectQuotation.get("endDate").asText() : "";
            String description = jsonObjectQuotation.get("description") != null ?
                    jsonObjectQuotation.get("description").asText() : "";
            String zoneDelivery = jsonObjectQuotation.get("zoneDelivery") != null ?
                    jsonObjectQuotation.get("zoneDelivery").asText() : "";


            Quotation quotation = new Quotation();
            quotation.setProductId(productId);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate beginParsedDate = LocalDate.parse(beginDate, dateFormatter);
            LocalDate endParsedDate = LocalDate.parse(endDate, dateFormatter);
            Supplier supplier = new Supplier();
            supplier.setId(supplierId);
            quotation.setSupplier(supplier);
            quotation.setNumber(number);
            quotation.setUnitType(UnitType.valueOf(unit));
            quotation.setBeginDate(beginParsedDate);
            quotation.setEndDate(endParsedDate);
            quotation.setDescription(description);
            quotation.setZoneDelivery(zoneDelivery);
            quotation.setMass(mass);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timeNow = Timestamp.valueOf(now);
            quotation.setCreatedAt(timeNow);
            Timestamp updateAt = Timestamp.valueOf(now);
            quotation.setUpdatedAt(updateAt);
            quotation.setStatus(1);

            Quotation saveQuotation = quotationRepository.save(quotation);
            if (saveQuotation.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("OK", "Successfully", ""));
            }
            else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ERROR", "Can not create a quotation", ""));
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred", e.getMessage()));
        }
    }

    public ResponseEntity<Object> updateStatus(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (json == null || json.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Empty JSON", ""));
            }
            JsonNode jsonObjectUpdate = objectMapper.readTree(json);
            Integer quotation_id = jsonObjectUpdate.get("quotation_id") != null ?
                    jsonObjectUpdate.get("quotation_id").asInt() : 1;
            Integer status = jsonObjectUpdate.get("status") != null ?
                    jsonObjectUpdate.get("status").asInt() : 1;
            Integer employeeId = jsonObjectUpdate.get("employeeId") != null ?
                    jsonObjectUpdate.get("employeeId").asInt() : 1;

            Optional<Quotation> optionalQuotation = quotationRepository.findById(quotation_id);
            if (optionalQuotation.isPresent()) {
                Quotation quotation = optionalQuotation.get();
                quotation.setEmployeeId(employeeId);
                LocalDateTime now = LocalDateTime.now();
                quotation.setDateConfirmed(LocalDate.from(now));
                quotation.setStatus(status);
                Quotation saveQuotation = quotationRepository.save(quotation);
                // After employee approved, supplyCapacity is created in DB
                if (status == 2) {
                    SupplyCapacity supplyCapacity = new SupplyCapacity();
                    supplyCapacity.setProductId(quotation.getProductId());
                    supplyCapacity.setMass(quotation.getMass());
                    supplyCapacity.setNumber(quotation.getNumber());
                    supplyCapacity.setBeginDate(quotation.getBeginDate());
                    supplyCapacity.setEndDate(quotation.getEndDate());
                    supplyCapacity.setUnitType(quotation.getUnitType());
                    supplyCapacity.setWarehouseDelivery("Dang test");
                    supplyCapacityRepository.save(supplyCapacity);
                }
                if (saveQuotation.getId() != null) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseObject("OK", "Successfully", ""));
                }
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", "Can not update a quotation", ""));

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred", e.getMessage()));
        }
    }

    public ResponseEntity<Object> createRequestChangeSupplyCapacity(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (json == null || json.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Empty JSON", ""));
            }
            JsonNode jsonObjectRequest = objectMapper.readTree(json);
            Integer productId = jsonObjectRequest.get("productId") != null ?
                    jsonObjectRequest.get("productId").asInt() : 1;
            Integer supplierId = jsonObjectRequest.get("supplierId") != null ?
                    jsonObjectRequest.get("supplierId").asInt() : 1;
            Double number = jsonObjectRequest.get("number") != null ?
                    jsonObjectRequest.get("number").asDouble() : 1;
            Double mass = jsonObjectRequest.get("mass") != null ?
                    jsonObjectRequest.get("mass").asDouble() : 1;
            String unit = jsonObjectRequest.get("unit") != null ?
                    jsonObjectRequest.get("unit").asText() : "";
            String beginDate = jsonObjectRequest.get("beginDate") != null ?
                    jsonObjectRequest.get("beginDate").asText() : "";
            String endDate = jsonObjectRequest.get("endDate") != null ?
                    jsonObjectRequest.get("endDate").asText() : "";
            String warehouseDelivery = jsonObjectRequest.get("warehouseDelivery") != null ?
                    jsonObjectRequest.get("warehouseDelivery").asText() : "";
            SupplyCapacity supplyCapacity = new SupplyCapacity();
            supplyCapacity.setProductId(productId);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate beginParsedDate = LocalDate.parse(beginDate, dateFormatter);
            LocalDate endParsedDate = LocalDate.parse(endDate, dateFormatter);
            Supplier supplier = new Supplier();
            supplier.setId(supplierId);
            supplyCapacity.setSupplier(supplier);
            supplyCapacity.setNumber(number);
            supplyCapacity.setUnitType(UnitType.valueOf(unit));
            supplyCapacity.setBeginDate(beginParsedDate);
            supplyCapacity.setEndDate(endParsedDate);
            supplyCapacity.setWarehouseDelivery(warehouseDelivery);
            supplyCapacity.setMass(mass);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timeNow = Timestamp.valueOf(now);
            supplyCapacity.setCreatedAt(timeNow);
            Timestamp updateAt = Timestamp.valueOf(now);
            supplyCapacity.setUpdatedAt(updateAt);
            supplyCapacity.setStatus(1);

            SupplyCapacity saveSupplyCapacity = supplyCapacityRepository.save(supplyCapacity);
            if (saveSupplyCapacity.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("OK", "Successfully", ""));
            }
            else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ERROR", "Can not create a request", ""));
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred", e.getMessage()));
        }
    }


    public ResponseEntity<ResponseObject> findAll() {
        Map<String, Object> results = new TreeMap<String, Object>();
        List<Supplier> supplierList = null;
        try {
            supplierList = supplierInterface.findAll();
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        results.put("data", supplierList);
        if (results.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", results));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

}
