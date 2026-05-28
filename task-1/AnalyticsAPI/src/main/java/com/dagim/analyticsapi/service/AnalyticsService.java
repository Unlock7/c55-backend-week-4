package com.yourname.analyticsapi.service;

import com.yourname.analyticsapi.dto.request.AnalyticsRecordRequest;
import com.yourname.analyticsapi.dto.response.AnalyticsSummaryResponse;
import com.yourname.analyticsapi.exception.NotFoundException;
import com.yourname.analyticsapi.model.AnalyticsRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final Map<String, AnalyticsRecord> storage = new HashMap<>();

    public AnalyticsRecord create(AnalyticsRecordRequest request) {
        String id = UUID.randomUUID().toString();
        AnalyticsRecord record = new AnalyticsRecord(
                id,
                request.getEventType(),
                request.getEventSource(),
                request.getSessionId(),
                request.getTimestamp()
        );
        storage.put(id, record);
        return record;
    }

    public List<AnalyticsRecord> findAll(
            String eventType,
            String eventSource,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        return storage.values().stream()
                .filter(r -> eventType == null || r.getEventType().equals(eventType))
                .filter(r -> eventSource == null || r.getEventSource().equals(eventSource))
                .filter(r -> startTime == null || !r.getTimestamp().isBefore(startTime))
                .filter(r -> endTime == null || !r.getTimestamp().isAfter(endTime))
                .sorted(Comparator.comparing(AnalyticsRecord::getTimestamp))
                .collect(Collectors.toList());
    }

    public AnalyticsRecord findById(String id) {
        AnalyticsRecord record = storage.get(id);
        if (record == null) {
            throw new NotFoundException("Analytics record with id " + id + " not found");
        }
        return record;
    }

    public AnalyticsRecord replace(String id, AnalyticsRecordRequest request) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Analytics record with id " + id + " not found");
        }

        AnalyticsRecord updated = new AnalyticsRecord(
                id,
                request.getEventType(),
                request.getEventSource(),
                request.getSessionId(),
                request.getTimestamp()
        );
        storage.put(id, updated);
        return updated;
    }

    public void delete(String id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Analytics record with id " + id + " not found");
        }
        storage.remove(id);
    }

    public AnalyticsSummaryResponse getSummary(
            String eventType,
            String eventSource,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        List<AnalyticsRecord> filtered = findAll(eventType, eventSource, startTime, endTime);

        long totalRecords = filtered.size();

        Map<String, Long> totalsByEventType = filtered.stream()
                .collect(Collectors.groupingBy(
                        AnalyticsRecord::getEventType,
                        Collectors.counting()
                ));

        long uniqueSessions = filtered.stream()
                .map(AnalyticsRecord::getSessionId)
                .distinct()
                .count();

        return new AnalyticsSummaryResponse(totalRecords, totalsByEventType, uniqueSessions);
    }
}
