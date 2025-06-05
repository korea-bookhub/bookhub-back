package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.Branch;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
   List<Branch> findByBranchName(String branchName);

    boolean existsByBranchName(@NotNull String branchName);

    List<Branch> findByBranchLocationContaining(String branchLocation);
}
