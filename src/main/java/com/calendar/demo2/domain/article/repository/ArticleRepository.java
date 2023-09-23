package com.calendar.demo2.domain.article.repository;

import com.calendar.demo2.domain.article.entity.Article;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> firndByOrderByIdDesc(PageRequest pageRequest);
    List<Article> findByIdLessThanOrderByIdDesc(Long id,PageRequest pageRequest);
    List<Article> findByEventDateBetweenOrderByEventDateAsc(LocalDate startDate,LocalDate endDate);
}
