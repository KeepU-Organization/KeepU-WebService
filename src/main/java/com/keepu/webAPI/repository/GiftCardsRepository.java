package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.GiftCards;
import com.keepu.webAPI.model.Stores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiftCardsRepository extends JpaRepository<GiftCards, Integer> {
    @Query("SELECT g FROM GiftCards g WHERE g.store = :store AND g.isRedeemed = false")
    List<GiftCards> findByStore(@Param("store") Stores store);
}