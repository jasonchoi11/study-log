package me.minikuma.restapispring.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import me.minikuma.restapispring.accounts.Account;
import me.minikuma.restapispring.accounts.AccountSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // optional
    private int basePrice;   // optional
    private int maxPrice;    // optional
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
    @ManyToOne
    @JsonSerialize(using = AccountSerializer.class)
    private Account manager;

    public void update() {
        this.free = this.basePrice == 0 && this.maxPrice == 0;
        this.offline = !checkString(this.location);
        this.eventStatus = EventStatus.DRAFT;
    }

    private boolean checkString(String str) {
        return str == null || str.isBlank();
    }
}