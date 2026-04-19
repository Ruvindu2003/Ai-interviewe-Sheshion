package com.example.Ai.interviewe.Sheshion.service;

import com.example.Ai.interviewe.Sheshion.model.IntervieweAnswer;
import com.example.Ai.interviewe.Sheshion.model.IntervieweQuestions;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweAnswerRepository;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweQuestionRepository;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntervieweAnswerService {

    private final IntervieweAnswerRepository answerRepository;
    private final IntervieweQuestionRepository questionRepository;
    private final IntervieweSessionRepository sessionRepository;
    private final AiService aiService;

    public IntervieweAnswer IntervieweAnswersubmitAnswer(Long questionId, String answerText) {
        IntervieweQuestions question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // 1. Evaluate with AI
        String evaluation = aiService.evaluateAnswer(question.getQuestionText(), answerText);
        // Assuming format: [Score]/10 | [Feedback]
        String[] parts = evaluation.split("\\|");
        double score = 0.0;
        String feedback = "";
        if (parts.length >= 1) {
            try {
                // Remove everything except numbers and dots from the first part
                String scorePart = parts[0].replaceAll("[^0-9.]", "").trim();
                score = Double.parseDouble(scorePart);
                // If it captured 810 from 8/10, fix it (common for 8/10)
                if (score > 10 && parts[0].contains("/10")) {
                     score = score / 10;
                }
            } catch (Exception e) {}
        }
        if (parts.length >= 2) {
            feedback = parts[1].trim();
        }

        // 2. Save or Update Answer
        IntervieweAnswer answer = answerRepository.findByQuestion(question)
                .orElse(new IntervieweAnswer());
        
        answer.setQuestion(question);
        answer.setAnswerText(answerText);
        answer.setScore(score);
        answer.setFeedback(feedback);
        
        return answerRepository.save(answer);
    }
}
