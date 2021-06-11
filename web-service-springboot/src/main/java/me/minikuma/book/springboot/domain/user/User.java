package me.minikuma.book.springboot.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.minikuma.book.springboot.domain.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity @Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String picture;


}
