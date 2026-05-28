package com.yourname.analyticsapi.controller;

import com.yourname.analyticsapi.dto.request.AnalyticsRecordRequest;
import com.yourname.analyticsapi.dto.response.AnalyticsRecordResponse;
import com.yourname.analyticsapi.dto.response.AnalyticsSummaryResponse;
import com.yourname.analyticsapi.model.AnalyticsRecord;
import com.yourname.analyticsapi.service.AnalyticsService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @PostMapping
    public AnalyticsRecordResponse create(@Valid @RequestBody AnalyticsRecordRequest request) {
        AnalyticsRecord record = service.create(request);
        return toResponse(record);
    }

    @GetMapping
    public List<AnalyticsRecordResponse> list(
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String eventSource,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        return service.findAll(eventType, eventSource, startTime, endTime)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public AnalyticsRecordResponse getOne(@PathVariable String id) {
        return toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    public AnalyticsRecordResponse replace(
            @PathVariable String id,
            @Valid @RequestBody AnalyticsRecordRequest request
    ) {
        return toResponse(service.replace(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/summary")
    public AnalyticsSummaryResponse summary(
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String eventSource,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        return service.getSummary(eventType, eventSource, startTime, endTime);
    }

    private AnalyticsRecordResponse toResponse(AnalyticsRecord record) {
        return new AnalyticsRecordResponse(
                record.getId(),
                record.getEventType(),
                record.getEventSource(),
                record.getSessionId(),
                record.getTimestamp()
        );
    }
}
