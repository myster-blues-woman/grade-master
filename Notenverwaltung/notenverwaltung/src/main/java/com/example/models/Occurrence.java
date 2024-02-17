package com.example.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Occurrence {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private OccurrenceRepetition repetition;

    public Occurrence(LocalDateTime startDate, LocalDateTime endDate, OccurrenceRepetition repetition) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.repetition = repetition;
    }

    public Occurrence() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public OccurrenceRepetition getRepetition() {
        return repetition;
    }

    public void setRepetition(OccurrenceRepetition repetition) {
        this.repetition = repetition;
    }

}
