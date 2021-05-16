package me.minikuma.restapispring.events;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class EventResource extends EntityModel<Event> {
    public EventResource(Event event, Link... links) {
        EntityModel.of(event, links);
    }
}
