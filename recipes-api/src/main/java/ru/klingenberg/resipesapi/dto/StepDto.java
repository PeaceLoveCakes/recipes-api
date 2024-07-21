package ru.klingenberg.resipesapi.dto;

import lombok.Getter;
import lombok.Setter;
import ru.klingenberg.resipesapi.db.entity.Step;

@Getter
@Setter
public class StepDto {

    private String id;

    private String name;

    private String text;

    public static StepDto from(Step step){
        return new StepDto()
                .setId(step.getId())
                .setText(step.getText())
                .setName(step.getName());
    }
}
