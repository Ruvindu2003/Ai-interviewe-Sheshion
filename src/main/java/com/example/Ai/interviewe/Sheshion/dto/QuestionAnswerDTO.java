package com.example.Ai.interviewe.Sheshion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDTO {
    private String questionText;
    private String answerText;
    private Double score;
    private String feedback;
}
