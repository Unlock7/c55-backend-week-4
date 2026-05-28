package com.dagim.analyticsapi.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AnalyticsRecordResponse {

    private String id;
    private String eventType;
    private String eventSource;
    private String sessionId;
    private LocalDateTime timestamp;
}
