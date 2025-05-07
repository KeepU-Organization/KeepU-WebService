package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.GiftCards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCardsRepository extends JpaRepository<GiftCards, Integer> {
}