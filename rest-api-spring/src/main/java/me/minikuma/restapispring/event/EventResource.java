package me.minikuma.restapispring.event;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {
    public EventResource(Event event, Link... links) {
        super(event, links);
        // Self link 등록
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
