package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.InvitationCodes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationCodesRepository extends JpaRepository<InvitationCodes, Integer> {
}