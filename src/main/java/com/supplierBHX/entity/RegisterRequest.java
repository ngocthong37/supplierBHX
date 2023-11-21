package com.supplierBHX.entity;

import com.supplierBHX.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String userName;
  private String email;
  private Role role;
  private Boolean status;
  private Integer supplierId;
}
