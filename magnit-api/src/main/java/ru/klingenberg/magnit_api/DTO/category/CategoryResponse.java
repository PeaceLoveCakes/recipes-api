package ru.klingenberg.magnit_api.DTO.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<Category> categories;
}
