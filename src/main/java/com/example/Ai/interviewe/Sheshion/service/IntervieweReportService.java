package com.example.Ai.interviewe.Sheshion.service;

import com.example.Ai.interviewe.Sheshion.dto.IntervieweReportDTO;
import com.example.Ai.interviewe.Sheshion.dto.QuestionAnswerDTO;
import com.example.Ai.interviewe.Sheshion.model.IntervieweAnswer;
import com.example.Ai.interviewe.Sheshion.model.IntervieweQuestions;
import com.example.Ai.interviewe.Sheshion.model.IntervieweReport;
import com.example.Ai.interviewe.Sheshion.model.IntervieweSession;
import com.example.Ai.interviewe.Sheshion.model.IntervieweStrength;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweReportRepository;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweSessionRepository;
import com.example.Ai.interviewe.Sheshion.repository.IntervieweStrengthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IntervieweReportService {

    private final IntervieweReportRepository reportRepository;
    private final IntervieweSessionRepository sessionRepository;
    private final IntervieweStrengthRepository strengthRepository;
    private final AiService aiService;

    @Transactional
    public IntervieweReportDTO generateReport(Long sessionId) {
        IntervieweSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // 1. Calculate Overall Score
        List<IntervieweQuestions> questions = session.getQuestions();
        double totalScore = 0;
        int answeredCount = 0;
        List<QuestionAnswerDTO> qaDtos = new ArrayList<>();

        for (IntervieweQuestions q : questions) {
            if (q.getAnswer() != null) {
                totalScore += q.getAnswer().getScore();
                answeredCount++;
                qaDtos.add(new QuestionAnswerDTO(
                        q.getQuestionText(),
                        q.getAnswer().getAnswerText(),
                        q.getAnswer().getScore(),
                        q.getAnswer().getFeedback()
                ));
            }
        }

        double overallScore = answeredCount > 0 ? totalScore / answeredCount : 0;

        // 2. Generate AI Report
        StringBuilder transcript = new StringBuilder();
        for (QuestionAnswerDTO qa : qaDtos) {
            transcript.append("Q: ").append(qa.getQuestionText()).append("\n");
            transcript.append("A: ").append(qa.getAnswerText()).append("\n");
            transcript.append("Score: ").append(qa.getScore()).append("/10\n\n");
        }

        String aiReport = aiService.generatePerformanceReport(transcript.toString());
        String[] parts = aiReport.split("\\|");
        String summary = parts.length > 0 ? parts[0].trim() : "Interview completed.";

        // 3. Save Report (Hardened Delete-then-Insert to avoid duplicate session_id)
        reportRepository.findBySessionId(sessionId).ifPresent(oldReport -> {
            strengthRepository.deleteAll(oldReport.getStrengths());
            reportRepository.delete(oldReport);
            reportRepository.flush(); // Force delete from DB now
        });

        IntervieweReport report = new IntervieweReport();
        report.setSession(session);
        report.setOverallScore(overallScore);
        report.setSummary(summary);
        
        report = reportRepository.save(report);
        reportRepository.flush();

        // 4. Save Strengths/Weaknesses
        for (int i = 1; i < parts.length; i++) {
            String[] metricParts = parts[i].split(":");
            if (metricParts.length >= 2) {
                String title = metricParts[0].trim();
                String desc = metricParts[1].trim();
                IntervieweStrength.MetricType type = title.toLowerCase().contains("weakness") ? 
                        IntervieweStrength.MetricType.WEAKNESS : IntervieweStrength.MetricType.STRENGTH;
                saveStrength(report, title, desc, type);
            }
        }

        // 5. Return DTO
        return new IntervieweReportDTO(
                sessionId,
                overallScore,
                report.getSummary(),
                qaDtos,
                getMetrics(report, IntervieweStrength.MetricType.STRENGTH),
                getMetrics(report, IntervieweStrength.MetricType.WEAKNESS)
        );
    }

    private void saveStrength(IntervieweReport report, String title, String desc, IntervieweStrength.MetricType type) {
        IntervieweStrength strength = new IntervieweStrength();
        strength.setReport(report);
        strength.setTitle(title);
        strength.setDescription(desc);
        strength.setType(type);
        strengthRepository.save(strength);
    }

    private List<IntervieweReportDTO.MetricDTO> getMetrics(IntervieweReport report, IntervieweStrength.MetricType type) {
        return strengthRepository.findAll().stream()
                .filter(s -> s.getReport().getId().equals(report.getId()) && s.getType() == type)
                .map(s -> new IntervieweReportDTO.MetricDTO(s.getTitle(), s.getDescription()))
                .collect(Collectors.toList());
    }
}
