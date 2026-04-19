package com.example.Ai.interviewe.Sheshion.controller;

import com.example.Ai.interviewe.Sheshion.model.IntervieweAnswer;
import com.example.Ai.interviewe.Sheshion.service.IntervieweAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for Next.js frontend
public class IntervieweAnswerController {

    private final IntervieweAnswerService answerService;

    @PostMapping("/submit")
    public ResponseEntity<IntervieweAnswer> submitAnswer(
            @RequestParam("questionId") Long questionId,
            @RequestBody String answerText) {
        
        IntervieweAnswer answer = answerService.IntervieweAnswersubmitAnswer(questionId, answerText);
        return ResponseEntity.ok(answer);
    }
}
