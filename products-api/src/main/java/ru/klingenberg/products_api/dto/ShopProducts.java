package ru.klingenberg.products_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopProducts {
    private String shopName;
    private List<ProductDto> elements;
}
