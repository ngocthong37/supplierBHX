package com.supplierBHX.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.Enum.StatusType;
import com.supplierBHX.Enum.UnitType;
import com.supplierBHX.dto.InvoiceDTO;
import com.supplierBHX.dto.QuotationDTO;
import com.supplierBHX.dto.SupplyCapacityDTO;
import com.supplierBHX.entity.*;
import com.supplierBHX.repository.QuotationRepository;
import com.supplierBHX.repository.SupplyCapacityRepository;
import com.supplierBHX.repository.WarehouseDeliveryRepository;
import com.supplierBHX.repository.ZoneDeliveryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SupplierService {
    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private SupplyCapacityRepository supplyCapacityRepository;

    @Autowired
    private ZoneDeliveryRepository zoneDeliveryRepository;

    @Autowired
    private WarehouseDeliveryRepository warehouseDeliveryRepository;

    @Autowired
    private ModelMapper modelMapper;


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
            String unit = jsonObjectQuotation.get("unit") != null ?
                    jsonObjectQuotation.get("unit").asText() : "";
            String beginDate = jsonObjectQuotation.get("beginDate") != null ?
                    jsonObjectQuotation.get("beginDate").asText() : "";
            String endDate = jsonObjectQuotation.get("endDate") != null ?
                    jsonObjectQuotation.get("endDate").asText() : "";
            String description = jsonObjectQuotation.get("description") != null ?
                    jsonObjectQuotation.get("description").asText() : "";
            Integer accountId = jsonObjectQuotation.get("accountId") != null ?
                    jsonObjectQuotation.get("accountId").asInt() : 1;
            Double price = jsonObjectQuotation.get("price") != null ?
                    jsonObjectQuotation.get("price").asDouble() : -1;

            JsonNode zoneDeliveryList = jsonObjectQuotation.get("zoneDeliveryList");

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
            quotation.setPrice(price);
            Account account = new Account();
            account.setId(accountId);

            quotation.setAccount(account);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timeNow = Timestamp.valueOf(now);
            quotation.setCreatedAt(timeNow);
            Timestamp updateAt = Timestamp.valueOf(now);
            quotation.setUpdatedAt(updateAt);
            quotation.setStatus(StatusType.valueOf("PROCESSING"));

            List<ZoneDelivery> zoneDeliveries = new ArrayList<>();
            if (zoneDeliveryList.isArray()) {
                for (JsonNode zoneDeliveryJson : zoneDeliveryList) {
                    ZoneDelivery zoneDelivery = new ZoneDelivery();
                    zoneDelivery.setAddress(zoneDeliveryJson.get("address").asText());
                    zoneDeliveries.add(zoneDelivery);
                }
            }

            Quotation saveQuotation = quotationRepository.save(quotation);
            for (ZoneDelivery zoneDelivery : zoneDeliveries) {
                zoneDelivery.setQuotation(saveQuotation);
                zoneDeliveryRepository.save(zoneDelivery);
            }
            if (saveQuotation.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("OK", "Successfully", ""));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ERROR", "Can not create a quotation", ""));
            }
        } catch (Exception e) {
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
            String status = jsonObjectUpdate.get("status") != null ?
                    jsonObjectUpdate.get("status").asText() : "";
            Integer employeeId = jsonObjectUpdate.get("employeeId") != null ?
                    jsonObjectUpdate.get("employeeId").asInt() : 1;
            Optional<Quotation> optionalQuotation = quotationRepository.findById(quotation_id);
            if (optionalQuotation.isPresent()) {
                Quotation quotation = optionalQuotation.get();
                quotation.setEmployeeId(employeeId);
                LocalDateTime now = LocalDateTime.now();
                quotation.setDateConfirmed(LocalDate.from(now));
                quotation.setStatus(StatusType.valueOf(status));
                Quotation saveQuotation = quotationRepository.save(quotation);
                if (saveQuotation.getId() != null) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseObject("OK", "Successfully", ""));
                }
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", "Can not update a quotation", ""));
        } catch (Exception e) {
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
            String unit = jsonObjectRequest.get("unit") != null ?
                    jsonObjectRequest.get("unit").asText() : "";
            String beginDate = jsonObjectRequest.get("beginDate") != null ?
                    jsonObjectRequest.get("beginDate").asText() : "";
            String endDate = jsonObjectRequest.get("endDate") != null ?
                    jsonObjectRequest.get("endDate").asText() : "";
            Integer accountId = jsonObjectRequest.get("accountId") != null ?
                    jsonObjectRequest.get("accountId").asInt() : 1;

            JsonNode warehouseDeliveryList = jsonObjectRequest.get("warehouseDeliveryList");

            SupplyCapacity supplyCapacity = new SupplyCapacity();

            Product product = new Product();
            product.setId(productId);

            supplyCapacity.setProduct(product);

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

            Account account = new Account();
            account.setId(accountId);
            supplyCapacity.setAccount(account);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timeNow = Timestamp.valueOf(now);
            supplyCapacity.setCreatedAt(timeNow);
            Timestamp updateAt = Timestamp.valueOf(now);
            supplyCapacity.setUpdatedAt(updateAt);
            supplyCapacity.setStatus(StatusType.valueOf("PROCESSING"));

            List<WarehouseDelivery> warehouseDeliveries = new ArrayList<>();
            if (warehouseDeliveryList.isArray()) {
                for (JsonNode warehouseDeliveryJson : warehouseDeliveryList) {
                    WarehouseDelivery warehouseDelivery = new WarehouseDelivery();
                    String address = warehouseDeliveryJson.get("address") != null ?
                            warehouseDeliveryJson.get("address").asText() : "";
                    Integer numberInWH = warehouseDeliveryJson.get("numberInWH") != null ?
                            warehouseDeliveryJson.get("numberInWH").asInt() : -1;
                    warehouseDelivery.setNumber(numberInWH);
                    warehouseDelivery.setAddress(address);
                    warehouseDeliveries.add(warehouseDelivery);
                }
            }
            SupplyCapacity saveSupplyCapacity = supplyCapacityRepository.save(supplyCapacity);
            for (WarehouseDelivery warehouseDelivery : warehouseDeliveries) {
                warehouseDelivery.setSupplyCapacity(supplyCapacity);
                warehouseDeliveryRepository.save(warehouseDelivery);
            }

            if (saveSupplyCapacity.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("OK", "Successfully", ""));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ERROR", "Can not create a request", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred", e.getMessage()));
        }
    }


    public ResponseEntity<ResponseObject> findAllQuotation() {
        List<QuotationDTO> supplierListDTO = null;
        try {
            supplierListDTO = quotationRepository.findAll().stream().map(quotation -> modelMapper.map(
                    quotation,
                    QuotationDTO.class
            )).toList();
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        if (supplierListDTO.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", supplierListDTO));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> getFilteredQuotations(Pageable pageable, Map<String, Object> filters) {
        Page<Quotation> quotationPage;
        if (filters != null && !filters.isEmpty()) {
            Map<String, Object> convertedFilters = convertFilters(filters);
            quotationPage = quotationRepository.findByFilters(
                    (List<StatusType>) convertedFilters.get("statusList"),
                    (LocalDate) convertedFilters.get("from"),
                    (LocalDate) convertedFilters.get("to"),
                    pageable);
        } else {
            quotationPage = quotationRepository.findAll(pageable);
        }
        return quotationPage.hasContent() ?
                ResponseEntity.ok(new ResponseObject("OK", "Successfully", quotationPage.getContent().stream().map(
                        quotation -> modelMapper.map(
                                quotation, QuotationDTO.class
                        )
                ))) :
                ResponseEntity.ok(new ResponseObject("Not found", "Not found", ""));
    }

    public ResponseEntity<ResponseObject> getFilteredSupplyCapacity(Pageable pageable, Map<String, Object> filters) {
        Page<SupplyCapacity> supplyCapacityPage;
        if (filters != null && !filters.isEmpty()) {
            Map<String, Object> convertedFilters = convertFilters(filters);
            supplyCapacityPage = supplyCapacityRepository.findByFilters(
                    (List<StatusType>) convertedFilters.get("statusList"),
                    (LocalDate) convertedFilters.get("from"),
                    (LocalDate) convertedFilters.get("to"),
                    (String) convertedFilters.get("search"),
                    pageable);
        } else {
            supplyCapacityPage = supplyCapacityRepository.findAll(pageable);
        }
        return supplyCapacityPage.hasContent() ?
                ResponseEntity.ok(new ResponseObject("OK", "Successfully", supplyCapacityPage.getContent().stream().map(
                        supplyCapacity -> modelMapper.map(
                                supplyCapacity, SupplyCapacityDTO.class
                        )
                ).toList())) :
                ResponseEntity.ok(new ResponseObject("Not found", "Not found", ""));
    }


    public ResponseEntity<ResponseObject> findQuotationById(Integer id) {
        Optional<QuotationDTO> quotation = quotationRepository.findById(id)
                .map(quotation1 -> modelMapper.map(quotation1, QuotationDTO.class));
        if (!quotation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", quotation));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findSupplyCapacityById(Integer id) {
        Optional<SupplyCapacityDTO> supplyCapacityDTO = supplyCapacityRepository.findById(id).map(
                supplyCapacity1 -> modelMapper.map(supplyCapacity1, SupplyCapacityDTO.class)
        );
        if (!supplyCapacityDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", supplyCapacityDTO));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<ResponseObject> findAllSupplyCapacity() {
        List<SupplyCapacityDTO> supplyCapacityListDTO = null;
        try {
            supplyCapacityListDTO = supplyCapacityRepository.findAll().stream().map(
                    supplyCapacity -> modelMapper.map(
                            supplyCapacity, SupplyCapacityDTO.class
                    )
            ).toList();
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        if (!supplyCapacityListDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", supplyCapacityListDTO));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }

    public ResponseEntity<Object> updateSupplyCapacityStatus(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (json == null || json.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Empty JSON", ""));
            }
            JsonNode jsonObjectUpdate = objectMapper.readTree(json);
            Integer supply_capacity_id = jsonObjectUpdate.get("supply_capacity_id") != null ?
                    jsonObjectUpdate.get("supply_capacity_id").asInt() : 1;
            String status = jsonObjectUpdate.get("status") != null ?
                    jsonObjectUpdate.get("status").asText() : "";
            Integer employeeId = jsonObjectUpdate.get("employeeId") != null ?
                    jsonObjectUpdate.get("employeeId").asInt() : 1;

            Optional<SupplyCapacity> optionalSupplyCapacity = supplyCapacityRepository.findById(supply_capacity_id);
            if (optionalSupplyCapacity.isPresent()) {
                SupplyCapacity supplyCapacity = optionalSupplyCapacity.get();
                supplyCapacity.setEmployeeId(employeeId);
                LocalDateTime now = LocalDateTime.now();
                supplyCapacity.setDateConfirmed(LocalDate.from(now));
                supplyCapacity.setStatus(StatusType.valueOf(status));
                var saveSupplyCapacity = supplyCapacityRepository.save(supplyCapacity);
                if (saveSupplyCapacity.getId() != null) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseObject("OK", "Successfully", ""));
                }
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", "Can not update a status", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred", e.getMessage()));
        }
    }

    private Map<String, Object> convertFilters(Map<String, Object> filters) {
        Map<String, Object> convertedFilters = new HashMap<>();

        if (filters.containsKey("search")) {
            String productName = (String) filters.get("search");
            convertedFilters.put("search", productName);
        }

        if (filters.containsKey("status")) {
            String statusStrings = (String) filters.get("status");
            String[] statusArray = statusStrings.split(",");
            List<StatusType> statusList = convertToStatusList(statusArray);
            convertedFilters.put("statusList", statusList);
        }

        if (filters.containsKey("from")) {
            String dateFromString = (String) filters.get("from");
            LocalDate dateFrom = LocalDate.parse(dateFromString, DateTimeFormatter.ISO_DATE);
            convertedFilters.put("from", dateFrom);
        }

        if (filters.containsKey("to")) {
            String dateToString = (String) filters.get("to");
            LocalDate dateTo = LocalDate.parse(dateToString, DateTimeFormatter.ISO_DATE);
            convertedFilters.put("to", dateTo);
        }
        return convertedFilters;
    }

    public List<StatusType> convertToStatusList(String[] statusStrings) {
        List<StatusType> statusList = new ArrayList<>();

        for (String statusString : statusStrings) {
            try {
                statusList.add(StatusType.valueOf(statusString.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid Status value: " + statusString);
            }
        }
        return statusList;
    }


}
