package com.supplierBHX.config;

import com.supplierBHX.entity.Problem;
import com.supplierBHX.entity.PurchaseOrderDetail;
import com.supplierBHX.entity.vm.ProblemVM;
import com.supplierBHX.entity.vm.PurchaseOrderDetailVM;
import com.supplierBHX.repository.AccountRepository;
import com.supplierBHX.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final AccountRepository accountRepository;
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        // Forward mapping from PurchaseOrderDetailVM to PurchaseOrderDetail
        modelMapper.addMappings(new PropertyMap<PurchaseOrderDetailVM, PurchaseOrderDetail>() {
            @Override
            protected void configure() {
                map().setProductName(source.getProductName());
                map().setUnitPrice(source.getUnitPrice());
                map().setVAT(source.getVAT());
                map().setDiscount(source.getDiscount());
                map().setQuantity(source.getQuantity());
                map().setMass(source.getMass());
                map().setNewQuantity(source.getNewQuantity());
                map().setNewMass(source.getNewMass());
                map().setFinalAmount(source.getFinalAmount());
            }
        });

        // Reverse mapping from PurchaseOrderDetail to PurchaseOrderDetailVM
        modelMapper.addMappings(new PropertyMap<PurchaseOrderDetail, PurchaseOrderDetailVM>() {
            @Override
            protected void configure() {
                map().setProductName(source.getProductName());
                map().setUnitPrice(source.getUnitPrice());
                map().setVAT(source.getVAT());
                map().setDiscount(source.getDiscount());
                map().setQuantity(source.getQuantity());
                map().setMass(source.getMass());
                map().setNewQuantity(source.getNewQuantity());
                map().setNewMass(source.getNewMass());
                map().setFinalAmount(source.getFinalAmount());
            }
        });


        return modelMapper;
    }
}
