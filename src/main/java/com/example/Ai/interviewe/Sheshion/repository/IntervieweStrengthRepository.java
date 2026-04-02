package com.example.Ai.interviewe.Sheshion.repository;

import com.example.Ai.interviewe.Sheshion.model.IntervieweStrength;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervieweStrengthRepository extends JpaRepository<IntervieweStrength, Long> {
}
