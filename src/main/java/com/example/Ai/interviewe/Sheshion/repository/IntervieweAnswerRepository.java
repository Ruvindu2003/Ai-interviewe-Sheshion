package com.example.Ai.interviewe.Sheshion.repository;

import com.example.Ai.interviewe.Sheshion.model.IntervieweAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervieweAnswerRepository extends JpaRepository<IntervieweAnswer, Long> {
}
