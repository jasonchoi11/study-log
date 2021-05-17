package me.minikuma.restapispring.events;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventResource implements RepresentationModelAssembler<Event, EntityModel<Event>> {
    @Override
    public EntityModel<Event> toModel(Event event) {
        Link withSelfRel = linkTo(EventController.class).slash(event.getId()).withSelfRel();
        return EntityModel.of(event,
                withSelfRel,
                withSelfRel.withRel("query-event"),
                withSelfRel.withRel("update-event")
        );
    }
}
