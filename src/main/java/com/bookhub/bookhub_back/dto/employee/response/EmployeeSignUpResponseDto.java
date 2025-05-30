package com.bookhub.bookhub_back.dto.employee.response;

import com.bookhub.bookhub_back.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSignUpResponseDto {
    private Employee employee;
}