package me.minikuma.business.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @NoArgsConstructor
public class Row {
    private Variety variety;
    private Integer seedsPerCell;
}
