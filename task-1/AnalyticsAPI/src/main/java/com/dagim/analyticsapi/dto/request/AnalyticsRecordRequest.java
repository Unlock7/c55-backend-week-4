package com.yourname.analyticsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AnalyticsRecordRequest {

    @NotBlank
    private String eventType;

    @NotBlank
    private String eventSource;

    @NotBlank
    private String sessionId;

    @NotNull
    private LocalDateTime timestamp;

    public String getEventType() {
        return eventType;
    }

    public String getEventSource() {
        return eventSource;
    }

    public String getSessionId() {
        return sessionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
