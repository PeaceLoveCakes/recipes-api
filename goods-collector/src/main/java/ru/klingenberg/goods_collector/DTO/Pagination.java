package ru.klingenberg.goods_collector.DTO;

import lombok.Data;

@Data
public class Pagination {
    private Integer number;
    private Integer size;
    private Long totalCount;
    private Integer totalPages;
    private boolean hasMore = false;
}
