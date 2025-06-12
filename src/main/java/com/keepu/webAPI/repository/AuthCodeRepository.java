package com.keepu.webAPI.repository;

import com.keepu.webAPI.dto.response.AuthCodeResponse;
import com.keepu.webAPI.model.AuthCode;
import com.keepu.webAPI.model.enums.AuthCodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthCodeRepository extends JpaRepository<AuthCode, Integer> {
    Optional<AuthCode> findByCode(String code);

    //solamente el ultimo agregado:
    @Query("SELECT a FROM AuthCode a WHERE a.userAuth.userId = :userId AND a.codeType = :codeType ORDER BY a.id DESC")
    Optional<AuthCode> findTopByUserIdAndCodeType(@Param("userId") Long userId, @Param("codeType") AuthCodeType codeType);

    Optional<AuthCode> findByUserAuthUserIdAndCode(Long userId, String code);

    AuthCodeType code(String code);
}