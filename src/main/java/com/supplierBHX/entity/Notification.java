package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.supplierBHX.Enum.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String message;
    private Integer BHX;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @JsonBackReference(value = "account-notification")
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
