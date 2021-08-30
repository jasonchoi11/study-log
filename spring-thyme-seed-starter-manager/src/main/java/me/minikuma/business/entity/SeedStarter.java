package me.minikuma.business.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class SeedStarter {
    private Integer id;
    private Date datePlanted;
    private boolean covered;
    private Type type;
    private Feature[] features;
    private List<Row>  rows = new ArrayList<>();
}
