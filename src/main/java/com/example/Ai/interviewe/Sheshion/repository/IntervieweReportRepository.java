package com.example.Ai.interviewe.Sheshion.repository;

import com.example.Ai.interviewe.Sheshion.model.IntervieweReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IntervieweReportRepository extends JpaRepository<IntervieweReport, Long> {
    
    @Query("SELECT r FROM IntervieweReport r WHERE r.session.id = :sessionId")
    Optional<IntervieweReport> findBySessionId(@Param("sessionId") Long sessionId);
}
