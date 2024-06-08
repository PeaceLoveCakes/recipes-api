package ru.klingenberg.magnit_api.DTO.goods;

import lombok.Data;

@Data
public class Pagination {
    private Integer number = 1;
    private Integer size = 36;
    private boolean hasMore = false;
    private Integer totalCount;
    private Integer totalPages;
}
