package ru.klingenberg.magnit_api.DTO.goods.request;

import lombok.Data;
import ru.klingenberg.magnit_api.DTO.goods.Pagination;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoodsRequest {

    private List<Integer> categoryIDs;
    private Boolean includeForAdults;
    private Boolean onlyDiscount;
    private Order order;
    private Pagination pagination;
    private String shopType;
    private String sortBy;
    private List<String>  storeCodes;
    private List<String> filters;

    public GoodsRequest(){
            this
                .setCategoryIDs(List.of(45723,45725,18061,45729,45727,4843,17637,17639,4854,4844,45717,45715,45719,4837,4842,18063,27187,38633,38637,17629,45757,17625,4845,38547,17673,17621,45751,45753,17493,17491,17489,17495,45761,4847,4848,4849,4850,4851,38037,46093))
                .setIncludeForAdults(true)
                .setOnlyDiscount(false)
                .setOrder(Order.desc)
                .setPagination(new Pagination()
                        .setNumber(1)
                        .setSize(36))
                .setShopType("1")
                .setSortBy("price")
                .setStoreCodes(List.of("782510"))
                .setFilters(new ArrayList<>());
    }
}
