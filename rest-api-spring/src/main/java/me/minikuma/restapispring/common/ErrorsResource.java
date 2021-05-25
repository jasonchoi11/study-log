package me.minikuma.restapispring.common;

import me.minikuma.restapispring.index.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorsResource extends EntityModel<Errors> {
    public static EntityModel<Errors> toModel(Errors content) {
        EntityModel<Errors> entityModel = EntityModel.of(content);
        entityModel.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
        return entityModel;
    }
}
