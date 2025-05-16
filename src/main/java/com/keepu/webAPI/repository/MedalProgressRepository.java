package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.MedalProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MedalProgressRepository extends JpaRepository<MedalProgress, Long> {

    @Query("SELECT m FROM MedalProgress m WHERE m.children.user.id = :userId")
    List<MedalProgress> findByUserId(@Param("userId") Long userId);

}
