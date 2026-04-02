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
@Table(name = "interviewe_questions")
public class IntervieweQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private IntervieweSession session;

    @Column(columnDefinition = "TEXT")
    private String questionText;

    private String category;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private IntervieweAnswer answer;
}
