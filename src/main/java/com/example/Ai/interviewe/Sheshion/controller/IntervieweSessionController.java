package com.example.Ai.interviewe.Sheshion.controller;

import com.example.Ai.interviewe.Sheshion.model.IntervieweSession;
import com.example.Ai.interviewe.Sheshion.service.IntervieweSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for Next.js frontend
public class IntervieweSessionController {

    private final IntervieweSessionService sessionService;

    @PostMapping("/start")
    public ResponseEntity<IntervieweSession> startSession(
            @RequestParam("candidateName") String candidateName,
            @RequestParam("cvFile") MultipartFile cvFile) {
        
        IntervieweSession session = sessionService.startSession(candidateName, cvFile);
        return ResponseEntity.ok(session);
    }

    private final com.example.Ai.interviewe.Sheshion.service.AiService aiService;

    @PostMapping("/{id}/interactive-response")
    public ResponseEntity<String> getInteractiveResponse(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> payload) {
        
        IntervieweSession session = sessionService.getSession(id);
        String cvContent = "Candidate: " + session.getCandidateName();
        
        String lastAnswer = payload.getOrDefault("lastAnswer", "");
        String conversationHistory = payload.getOrDefault("history", "");
        
        String aiResponse = aiService.getInteractiveInterviewResponse(cvContent, conversationHistory, lastAnswer);
        return ResponseEntity.ok(aiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntervieweSession> getSession(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSession(id));
    }
}
