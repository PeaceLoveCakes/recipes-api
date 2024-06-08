package ru.klingenberg.magnitgetgoods.DTO.request.category;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Category {

    private Integer id;
    private String name;
    private List<Category> children = new ArrayList<>();

    @Override
    public String toString(){
        return id + ": " + name.trim() +
                (children.isEmpty() ? "; " : " { \n" +
                children.stream().map(category -> "\t" + category.toString() + "\n").collect(Collectors.joining())
                + "}");
    }
}
