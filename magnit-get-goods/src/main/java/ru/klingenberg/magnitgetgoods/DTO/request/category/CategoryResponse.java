package ru.klingenberg.magnitgetgoods.DTO.request.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<Category> categories;
}
