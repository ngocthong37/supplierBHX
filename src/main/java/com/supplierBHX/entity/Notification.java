package com.supplierBHX.entity;

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
    private Enum<NotificationType> notificationType;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
