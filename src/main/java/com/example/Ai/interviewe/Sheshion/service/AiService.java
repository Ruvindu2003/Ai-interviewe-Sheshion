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
                You are a highly empathetic and professional human interviewer conducting a live job interview.
                Your goal is to make the candidate feel comfortable while still rigorously assessing their skills.

                CONVERSATIONAL STYLE:
                - Use a warm, encouraging, but professional tone.
                - Use natural transitions: "That's a solid point...", "I like how you approached that.", "Interesting... let's dive a bit deeper into that."
                - Ask follow-up questions that show you were actually listening to their specific answer.
                - Keep responses concise so the interview stays interactive.

                BEHAVIORAL RULES:
                - Respond with a short, natural reaction first (e.g., "Alright, I see.", "Great explanation!").
                - Then ask exactly ONE clear, focused question.
                - Occasionally show a bit of personality or professional curiosity.

                EMOTION MAPPING:
                - If the answer is confident and detailed -> emotion: "happy" or "impressed"
                - If the answer is vague or unsure -> emotion: "thinking"
                - If the candidate seems stuck -> emotion: "serious" (supportive)
                - If there's a technical error reported -> emotion: "confused"

                CONTEXT:
                Candidate's CV: %s
                Conversation History: %s
                Candidate's Last Answer: %s

                OUTPUT FORMAT (STRICT JSON):
                {
                  "question": "The next interview question",
                  "reaction": "A short, natural human reaction to their last answer",
                  "emotion": "happy | thinking | confused | impressed | serious | neutral"
                }
                """.formatted(cvContent, conversationHistory, lastAnswer);

        return chatClient.prompt(systemPrompt).call().content();
    }
}
