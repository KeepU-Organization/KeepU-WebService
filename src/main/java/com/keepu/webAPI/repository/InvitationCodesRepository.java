package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationCodesRepository extends JpaRepository<InvitationCodes, Integer> {
    Optional<InvitationCodes> findByInvitationCode(String invitationCode);

    boolean existsByInvitationCode(String invitationCode);

    Optional<InvitationCodes> findByUser(User user);
}