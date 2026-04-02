package com.example.Ai.interviewe.Sheshion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervieweSessionDTO {
    private Long id;
    private String candidateName;
    private String cvPath;
    private String status;
    private LocalDateTime createdAt;
}
