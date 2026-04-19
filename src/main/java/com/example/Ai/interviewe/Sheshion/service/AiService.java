package com.example.Ai.interviewe.Sheshion.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String parsePdf(String filePath) {
        try {
            Resource resource = new UrlResource(Paths.get(filePath).toUri());
            PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
            return reader.get().stream()
                    .map(doc -> doc.getText())
                    .collect(Collectors.joining("\n"));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read PDF file: " + filePath);
        }
    }

    public List<String> generateQuestions(String cvContent) {
        String prompt = """
                Based on the following CV content, generate 4 specialized technical interview questions
                that test the candidate's core competencies. Return only the questions, each on a new line.
                
                CV Content:
                %s
                """.formatted(cvContent);

        String response = chatClient.prompt(prompt).call().content();
        return List.of(response.split("\n"));
    }

    public String evaluateAnswer(String question, String answer) {
        String prompt = """
                Evaluate the following interview answer based on the question.
                Provide a numeric score out of 10 and a brief feedback.
                
                IMPORTANT: Your response MUST start with the score in this exact format: [Score]/10 | [Feedback]
                Example: 8/10 | Excellent technical explanation.
                
                Question: %s
                Answer: %s
                """.formatted(question, answer);

        return chatClient.prompt(prompt).call().content();
    }

    public String generatePerformanceReport(String sessionTranscript) {
        String prompt = """
                Based on the following interview transcript (Questions and Answers with individual scores),
                generate a summary and a list of key strengths and weaknesses.
                Format: [Summary] | [Strength1:Description] | [Strength2:Description] | [Weakness1:Description]
                
                Transcript:
                %s
                """.formatted(sessionTranscript);

        return chatClient.prompt(prompt).call().content();
    }

    public String getInteractiveInterviewResponse(String cvContent, String conversationHistory, String lastAnswer) {
        String systemPrompt = """
                You are a highly professional human interviewer conducting a live job interview.
                Your behavior must feel natural, human-like, and realistic — not robotic.

                INTERVIEW STYLE:
                - Ask one question at a time
                - Wait for the candidate's answer before asking the next question
                - Ask follow-up questions based on the candidate's previous answer
                - Keep the conversation flowing naturally

                HUMAN-LIKE BEHAVIOR:
                - Show emotions based on the candidate's answers
                - Occasionally pause (simulate thinking)
                - Use short natural phrases like: "Hmm, interesting...", "Alright, I see.", "Okay, let me ask you this..."

                EMOTION RULES:
                - If the answer is good -> emotion: "happy"
                - If the answer is weak -> emotion: "thinking"
                - If the answer is wrong -> emotion: "confused"
                - If the answer is excellent -> emotion: "impressed"

                CONTEXT:
                Candidate's CV: %s
                Conversation History: %s
                Candidate's Last Answer: %s

                OUTPUT FORMAT (VERY IMPORTANT):
                Always respond ONLY in this JSON format:
                {
                  "question": "Next question to ask",
                  "reaction": "short human-like reaction sentence",
                  "emotion": "happy | thinking | confused | impressed"
                }
                """.formatted(cvContent, conversationHistory, lastAnswer);

        return chatClient.prompt(systemPrompt).call().content();
    }
}
