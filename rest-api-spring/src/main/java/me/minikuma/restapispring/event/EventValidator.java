package me.minikuma.restapispring.event;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors) {
        // (1) base price > max price
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            errors.rejectValue("basePrice", "wrongValue", "basePrice is Wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "maxPrice is Wrong.");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        // (2) data valid
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())
            || endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())
            || endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is Wrong.");
        }
    }
}
