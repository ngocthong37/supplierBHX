package com.supplierBHX.entity;


import com.supplierBHX.Enum.ProblemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date dateCreated;
    private Date dateConfirmed;
    private Integer status;
    private String solution;
    private Integer employeeId;
    private Enum<ProblemType> problemType;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<ProblemDetail> problemDetails;


}
