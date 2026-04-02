package com.example.Ai.interviewe.Sheshion.repository;

import com.example.Ai.interviewe.Sheshion.model.IntervieweQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervieweQuestionRepository extends JpaRepository<IntervieweQuestions, Long> {
}
