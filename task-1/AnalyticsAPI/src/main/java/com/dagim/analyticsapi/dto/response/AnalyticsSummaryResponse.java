package com.yourname.analyticsapi.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class AnalyticsSummaryResponse {

    private long totalRecords;
    private Map<String, Long> totalsByEventType;
    private long uniqueSessions;
}
