package com.yourname.analyticsapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsRecord {

    private String id;
    private String eventType;
    private String eventSource;
    private String sessionId;
    private LocalDateTime timestamp;
}
