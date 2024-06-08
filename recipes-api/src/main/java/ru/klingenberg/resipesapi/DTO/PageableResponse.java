package ru.klingenberg.resipesapi.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PageableResponse<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private List<T> elements;
}
