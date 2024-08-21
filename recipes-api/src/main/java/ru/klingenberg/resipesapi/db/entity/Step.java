package ru.klingenberg.resipesapi.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "step")
@Getter
@Setter
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Recipe recipe;

}
