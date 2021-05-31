package me.minikuma.restapispring.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
@AllArgsConstructor
public class EventResource extends EntityModel<Event> {
    public static EntityModel<Event> toEventModel(Event event) {
        var eventEntityModel = EntityModel.of(event);
        WebMvcLinkBuilder selfLink = linkTo(EventController.class).slash(event.getId());
        eventEntityModel.add(selfLink.withSelfRel());
        eventEntityModel.add(linkTo(EventController.class).withRel("query-event"));
        eventEntityModel.add(selfLink.withRel("update-event"));
        eventEntityModel.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));
        return eventEntityModel;
    }

    public static EntityModel<Event> toEventSelfModel(Event event) {
        var eventEntityModel = EntityModel.of(event);
        WebMvcLinkBuilder selfLink = linkTo(EventController.class).slash(event.getId());
        eventEntityModel.add(selfLink.withSelfRel());
        return eventEntityModel;
    }

}
