package com.supplierBHX.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonBackReference(value = "supplier-problem")
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @JsonManagedReference(value = "problem-problemDetails")
    @OneToMany(mappedBy = "problem")
    private List<ProblemDetail> problemDetails;


}
