package com.full.stack.demo.core.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
//    private EmployeeResponseDto employee;
//    private SupplierResponseDto supplier;
}