package ru.klingenberg.goods_collector.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PageableResponse<T> {
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNo;
    private Integer pageSize;
    private boolean hasMore;
    private List<T> elements;

}
