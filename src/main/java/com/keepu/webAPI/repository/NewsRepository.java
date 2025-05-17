package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByUserId(Long userId);
}
