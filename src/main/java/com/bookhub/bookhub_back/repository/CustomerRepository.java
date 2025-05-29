package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
