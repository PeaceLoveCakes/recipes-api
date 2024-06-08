package ru.klingenberg.magnitgetgoods.DTO.request.goods;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoodsRequest {

    public GoodsRequest(Integer pageNo){
        pagination.setNumber(pageNo);
    }

    private List<Integer> categoryIDs = List.of(45723,
            45725,
            18061,
            45729,
            45727,
            4843,
            17637,
            17639,
            4854,
            4844,
            45717,
            45715,
            45719,
            4837,
            4842,
            18063,
            27187,
            38633,
            38637,
            17629,
            45757,
            17625,
            4845,
            38547,
            17673,
            17621,
            45751,
            45753,
            17493,
            17491,
            17489,
            17495,
            45761,
            4847,
            4848,
            4849,
            4850,
            4851,
            38037,
            46093);
    private Boolean includeForAdults = true;
    private Boolean onlyDiscount = false;
    private Order order = Order.desc;
    private Pagination pagination = new Pagination().setNumber(1).setSize(36);
    private String shopType = "1";
    private String sortBy = "price";
    List<String>  storeCodes = List.of("782510");
    List<String> filters = new ArrayList<>();
}
