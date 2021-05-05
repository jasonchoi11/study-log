package me.minikuma.restapispring.events;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class Event {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime endEnrollmentDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitEnrollment;
    private boolean offline;
    private boolean free;
    private EventStatus eventStatus;
}
