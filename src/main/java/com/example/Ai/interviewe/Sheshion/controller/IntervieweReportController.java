package com.example.Ai.interviewe.Sheshion.controller;

import com.example.Ai.interviewe.Sheshion.dto.IntervieweReportDTO;
import com.example.Ai.interviewe.Sheshion.service.IntervieweReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for Next.js frontend
public class IntervieweReportController {

    private final IntervieweReportService reportService;

    @GetMapping("/{sessionId}")
    public ResponseEntity<IntervieweReportDTO> getReport(@PathVariable Long sessionId) {
        IntervieweReportDTO report = reportService.generateReport(sessionId);
        return ResponseEntity.ok(report);
    }
}
