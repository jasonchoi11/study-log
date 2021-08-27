package me.minikuma.business.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@ToString @NoArgsConstructor
public class SeedStarter {
    private Integer id;
    private Date datePlanted;
    private boolean covered;
    private Type type = Type.PLASTIC;
    private Feature[] features;
    private List<Row> rows = new ArrayList<>();
}
