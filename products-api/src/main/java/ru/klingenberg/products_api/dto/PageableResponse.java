package ru.klingenberg.products_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableResponse<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private List<T> elements;
}
