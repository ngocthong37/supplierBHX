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
        NOT_CONFIRMED("NOT_CONFIRMED"),
        RECEIVED("RECEIVED"),
        APPROVED("APPROVED"),
        REJECTED("REJECTED"),
        DELIVERING("DELIVERING"),
        COMPLETED("COMPLETED"),
        EXPIRED("EXPIRED");
        private final String value;

        ConfirmedStatus(String value) {
            this.value = value;
        }

    }


    @Getter
    public enum ProblemType {
        Delay("Delay"),
        CannotDelivery("CannotDelivery"),
        MissingGoods("MissingGoods"),
        UpdateQuantity("UpdateQuantity");

        private final String value;

        ProblemType(String value) {
            this.value = value;
        }

    }
}
