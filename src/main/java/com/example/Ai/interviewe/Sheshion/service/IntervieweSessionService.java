package com.example.Ai.interviewe.Sheshion.service;

import com.example.Ai.interviewe.Sheshion.model.IntervieweQuestions;
import com.example.Ai.interviewe.Sheshion.model.IntervieweSession;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweQuestionRepository;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntervieweSessionService {

    private final IntervieweSessionRepository sessionRepository;
    private final IntervieweQuestionRepository questionRepository;
    private final FileStorageService fileStorageService;
    private final AiService aiService;

    @Transactional
    public IntervieweSession startSession(String candidateName, MultipartFile cvFile) {
        // 1. Store File
        String filePath = fileStorageService.storeFile(cvFile);

        // 2. Create Session
        IntervieweSession session = new IntervieweSession();
        session.setCandidateName(candidateName);
        session.setCvPath(filePath);
        session = sessionRepository.save(session);

        // 3. Generate Questions
        String cvContent = aiService.parsePdf(filePath);
        List<String> questionTexts = aiService.generateQuestions(cvContent);

        // 4. Save Questions
        for (String text : questionTexts) {
            if (text.trim().isEmpty()) continue;
            IntervieweQuestions question = new IntervieweQuestions();
            question.setSession(session);
            question.setQuestionText(text);
            question.setCategory("Technical");
            questionRepository.save(question);
        }

        return session;
    }

    public IntervieweSession getSession(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }
}
