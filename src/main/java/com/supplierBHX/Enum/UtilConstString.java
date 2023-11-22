package com.supplierBHX.Enum;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class UtilConstString {

    @Getter
    public enum DeliveryStatus {
        PENDING("PENDING"),
        IN_PROGRESS("IN_PROGRESS"),
        DELIVERED("DELIVERED"),
        CANCELED("CANCELED");
        private final String value;

        DeliveryStatus(String value) {
            this.value = value;
        }

    }

    @Getter
    public enum ConfirmedStatus {
        WAITING("WAITING"),
        PROCESSING("PROCESSING"),
        APPROVED("APPROVED"),
        REJECTED("REJECTED");

        private final String value;

        ConfirmedStatus(String value) {
            this.value = value;
        }

    }
}
