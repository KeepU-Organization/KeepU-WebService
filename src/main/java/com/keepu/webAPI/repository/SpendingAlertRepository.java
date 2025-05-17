
package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.SpendingAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpendingAlertRepository extends JpaRepository<SpendingAlert, Long> {
    Optional<SpendingAlert> findByUserIdAndCategoryAndActiveTrue(Integer userId, String category);
}
