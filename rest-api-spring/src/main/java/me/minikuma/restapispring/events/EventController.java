package me.minikuma.restapispring.events;

import lombok.RequiredArgsConstructor;
import me.minikuma.restapispring.common.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity<?> createEvents(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        eventValidator.validate(eventDto, errors);

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);

        event.update();

        Event newEvent = this.eventRepository.save(event);
        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        WebMvcLinkBuilder selfLink = linkTo(EventController.class).slash(newEvent.getId());

        EventResource eventResource = new EventResource(newEvent);
        eventResource.add(selfLink.withSelfRel());
        eventResource.add(linkTo(EventController.class).withRel("query-event"));
        eventResource.add(selfLink.withRel("update-event"));
        eventResource.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(createdUri).body(eventResource);
    }

    @GetMapping
    public ResponseEntity<?> queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
        Page<Event> page = this.eventRepository.findAll(pageable);
        var pagedResources = assembler.toModel(page);
        return ResponseEntity.ok(pagedResources);
    }


    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.toModel(errors));
    }
}
