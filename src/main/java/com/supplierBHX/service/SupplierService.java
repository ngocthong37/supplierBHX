package com.supplierBHX.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplierBHX.Enum.StatusType;
import com.supplierBHX.Enum.UnitType;
import com.supplierBHX.dto.*;
import com.supplierBHX.entity.*;
import com.supplierBHX.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private SupplyCapacityRepository supplyCapacityRepository;

    @Autowired
    private ZoneDeliveryRepository zoneDeliveryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private SupplierRepository supplierRepository;


    public List<String> uploadImage(List<MultipartFile> files, String namePath, Integer quotationId) {
        List<String> imageUrls;


        imageUrls = storageService.uploadImages(files, namePath);

        for (int i = 0; i < imageUrls.size(); i++) {
            quotationRepository.updateImage(imageUrls.get(i), quotationId);
        }
        quotationRepository.updateDefaultImage(imageUrls.get(0), quotationId);
        return imageUrls;
    }


    public ResponseEntity<Object> createQuotation(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (json == null || json.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Empty JSON", ""));
            }
            JsonNode jsonObjectQuotation = objectMapper.readTree(json);
            String productName = jsonObjectQuotation.get("productName") != null ?
                    jsonObjectQuotation.get("productName").asText() : "";
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
            JsonNode productImageList = jsonObjectQuotation.get("imageList");
            Quotation quotation = new Quotation();
            quotation.setProductName(productName);
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
            if (zoneDeliveryList != null && zoneDeliveryList.isArray()) {
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

            for (int i = 0; i < productImageList.size(); i++) {
                ProductImage productImage = new ProductImage();
                productImage.setQuotation(saveQuotation);
                productImage.setImageUrl("");
                productImageRepository.save(productImage);
            }

            if (saveQuotation.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("OK", "Successfully", saveQuotation.getId()));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ERROR", "Can not create a quotation", saveQuotation.getId()));
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
                    String[] cc = {"n20dccn152@student.ptithcm.edu.vn"};
                    String supplierEmail = null;
                    Optional<Account> accountOptional = accountRepository.findById(saveQuotation.getAccount().getId());
                    if (accountOptional.isPresent()) {
                        supplierEmail = accountOptional.get().getEmail();
                    }
                    emailService.sendMail(supplierEmail, cc, "Thông tin chào hàng của bạn đã được duyệt", "Đây là email test");
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

            SupplyCapacity saveSupplyCapacity = supplyCapacityRepository.save(supplyCapacity);

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
                        quotation -> {
                            QuotationDTO quotationDTO = modelMapper.map(quotation, QuotationDTO.class);
                            List<ZoneDeliveryDTO> zoneDeliveryDTOs = quotation.getZoneDeliveryList().stream()
                                    .map(zoneDelivery -> modelMapper.map(zoneDelivery, ZoneDeliveryDTO.class))
                                    .collect(Collectors.toList());
                            quotationDTO.setZoneDeliveries(zoneDeliveryDTOs);
                            return quotationDTO;
                        }
                ).collect(Collectors.toList())))
                :
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
                    String[] cc = {"n20dccn152@student.ptithcm.edu.vn"};
                    String supplierEmail = null;
                    Optional<Account> accountOptional = accountRepository.findById(saveSupplyCapacity.getAccount().getId());
                    if (accountOptional.isPresent()) {
                        supplierEmail = accountOptional.get().getEmail();
                    }
                    emailService.sendMail(supplierEmail, cc, "Thông tin chào hàng của bạn đã được duyệt", "Đây là email test");
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

    public ResponseEntity<ResponseObject> findSupplierById(Integer id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", supplier.stream().map(supplier1 -> modelMapper.map(supplier1, SupplierDTO.class)).collect(Collectors.toList())));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
        }
    }
}
