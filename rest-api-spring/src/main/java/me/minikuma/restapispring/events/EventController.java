package me.minikuma.restapispring.events;

import lombok.RequiredArgsConstructor;
import me.minikuma.restapispring.common.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
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
        EntityModel<Event> eventEntityModel = EventResource.toEventModel(event);
        return ResponseEntity.created(createdUri).body(eventEntityModel);
    }

    @GetMapping
    public ResponseEntity<?> queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
        Page<Event> page = this.eventRepository.findAll(pageable);
        var pagedResources = assembler.toModel(page, EventResource::toEventSelfModel);
        pagedResources.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Integer id) {
        Optional<Event> event = this.eventRepository.findById(id);
        if (event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Event selectedEvent = event.get();
        EntityModel<Event> eventModel = EventResource.toEventModel(selectedEvent);
        eventModel.add(Link.of("/docs/index.html#resources-events-get").withRel("profile"));
        return ResponseEntity.ok(eventModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto, Errors errors) {
        Optional<Event> event = this.eventRepository.findById(id);

        if (event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        this.eventValidator.validate(eventDto, errors);

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event existingEvent = event.get();

        this.modelMapper.map(eventDto, existingEvent);
        Event saveEvent = this.eventRepository.save(existingEvent);
        EntityModel<Event> entityModel = EventResource.toEventModel(saveEvent);
        entityModel.add(Link.of("/docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok(entityModel);
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.toModel(errors));
    }
}
