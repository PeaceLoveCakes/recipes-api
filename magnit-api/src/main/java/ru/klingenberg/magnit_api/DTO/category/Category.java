package ru.klingenberg.magnit_api.DTO.category;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Category {

    private Integer id;
    private String name;
    private List<Category> children = new ArrayList<>();

}
