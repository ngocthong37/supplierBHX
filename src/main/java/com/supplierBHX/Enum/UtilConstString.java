package com.supplierBHX.Enum;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class UtilConstString {

    @Getter
    public enum DeliveryStatus {
        WAITING("Waiting"),
        NOT_DELIVERED("Not Delivered yet"),
        IS_DELIVERING("Is Delivering"),
        DELIVERED("Delivered");
        private final String value;

        DeliveryStatus(String value) {
            this.value = value;
        }

    }

    @Getter
    public enum ConfirmedStatus {
        WAITING("Waiting"),
        PROCESSING("Processing"),
        APPROVED("Approved"),
        REJECTED("Rejected");

        private final String value;

        ConfirmedStatus(String value) {
            this.value = value;
        }

    }
}
