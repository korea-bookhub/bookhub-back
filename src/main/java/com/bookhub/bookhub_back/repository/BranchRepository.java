package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.Branch;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByBranchName(String branchName);

    boolean existsByBranchName(@NotNull String branchName);
}
