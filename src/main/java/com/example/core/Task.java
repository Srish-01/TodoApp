

package com.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;

public class Task {

    private long id;
    private String description;
    private LocalDate startDate;
    private LocalDate targetDate;
    private String status;

    public Task() {
        // Jackson deserialization
    }

    public Task(long id, String description, LocalDate startDate, LocalDate targetDate, String status) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.status = status;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    @JsonProperty
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty
    public LocalDate getStartDate() {
        return startDate;
    }

    @JsonProperty
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @JsonProperty
    public LocalDate getTargetDate() {
        return targetDate;
    }

    @JsonProperty
    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    @JsonProperty
    public String getStatus() {
        return status;
    }

    @JsonProperty
    public void setStatus(String status) {
        this.status = status;
    }
}
