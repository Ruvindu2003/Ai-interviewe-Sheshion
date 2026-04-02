package com.example.Ai.interviewe.Sheshion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interviewe_answers")
public class IntervieweAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private IntervieweQuestions question;

    @Column(columnDefinition = "TEXT")
    private String answerText;

    private String audioUrl;

    private Double score;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private LocalDateTime createdAt = LocalDateTime.now();
}
