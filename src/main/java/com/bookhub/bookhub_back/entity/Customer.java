package com.bookhub.bookhub_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_email", nullable = false, unique = true)
    private String customerEmail;

    @Column(name = "customer_phone_number", nullable = false, unique = true)
    private String customerPhoneNumber;

    @Column(name = "customer_address", nullable = false)
    private String customerAdderess;

    @Column(name = "customer_created_at", nullable = false)
    private LocalDate customerCreatedAt;
}
