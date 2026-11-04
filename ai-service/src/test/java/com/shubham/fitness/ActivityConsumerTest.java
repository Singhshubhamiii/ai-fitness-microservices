package com.shubham.fitness;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ActivityConsumerTest {
    private GeminiClient gemini;
    private RecommendationRepository repository;
    private ActivityConsumer consumer;

    @BeforeEach
    void setUp() {
        gemini = mock(GeminiClient.class);
        repository = mock(RecommendationRepository.class);
        consumer = new ActivityConsumer(new ObjectMapper(), gemini, repository);
    }

    @Test
    void validActivityEventGeneratesAndStoresRecommendation() throws Exception {
        String event = """
            {"id":"activity-123","userId":"user-7","type":"RUNNING","durationMinutes":30}
            """;
        when(gemini.generate(anyString())).thenReturn("Hydrate and recover.");
        when(repository.save(any(Recommendation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        consumer.receive(event);

        verify(gemini).generate(event);
        var saved = org.mockito.ArgumentCaptor.forClass(Recommendation.class);
        verify(repository).save(saved.capture());
        assertEquals("activity-123", saved.getValue().getActivityId());
        assertEquals("user-7", saved.getValue().getUserId());
        assertEquals("Hydrate and recover.", saved.getValue().getAdvice());
    }

    @Test
    void eventWithoutActivityIdIsIgnored() throws Exception {
        consumer.receive("{\"userId\":\"user-7\",\"type\":\"RUNNING\"}");

        verifyNoInteractions(gemini, repository);
    }
}
