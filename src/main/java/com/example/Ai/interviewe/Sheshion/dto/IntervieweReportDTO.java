package com.example.Ai.interviewe.Sheshion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervieweReportDTO {
    private Long sessionId;
    private Double overallScore;
    private String summary;
    private List<QuestionAnswerDTO> questionAnswers;
    private List<MetricDTO> strengths;
    private List<MetricDTO> weaknesses;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetricDTO {
        private String title;
        private String description;
    }
}
