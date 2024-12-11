package com.team.futureway.gemini.repository;

import com.team.futureway.gemini.entity.AiConsultationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiConsultationHistoryRepository extends JpaRepository<AiConsultationHistory, Long>{

    List<AiConsultationHistory> findByUserId(Long userId);
}
