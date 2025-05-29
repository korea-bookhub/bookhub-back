package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
