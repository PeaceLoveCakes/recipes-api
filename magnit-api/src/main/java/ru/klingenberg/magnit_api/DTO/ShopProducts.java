package ru.klingenberg.magnit_api.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ShopProducts {
    private String shopName;
    private List<ProductDto> elements;
    private boolean hasMore;
}
