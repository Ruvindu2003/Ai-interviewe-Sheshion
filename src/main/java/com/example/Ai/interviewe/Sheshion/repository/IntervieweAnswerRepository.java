package com.example.Ai.interviewe.Sheshion.repository;

import com.example.Ai.interviewe.Sheshion.model.IntervieweAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ai.interviewe.Sheshion.model.IntervieweQuestions;
import java.util.Optional;

@Repository
public interface IntervieweAnswerRepository extends JpaRepository<IntervieweAnswer, Long> {
    Optional<IntervieweAnswer> findByQuestion(IntervieweQuestions question);
}
