package com.supplierBHX.entity;

import com.supplierBHX.Enum.AccountType;
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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String password;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    private Enum<AccountType> accountType;

}
