package ru.klingenberg.magnit_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.klingenberg.magnit_api.DTO.PageableResponse;
import ru.klingenberg.magnit_api.DTO.ProductDto;
import ru.klingenberg.magnit_api.DTO.goods.response.Good;
import ru.klingenberg.magnit_api.DTO.goods.response.GoodsResponse;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductsService {

    @Value("${magnit.url}")
    private String url;

    private String shop = "Магнит";

    public PageableResponse<ProductDto> PageableResponseFromGoodsResponse(GoodsResponse goodsResponse){
        return new PageableResponse<ProductDto>()
                .setElements(goodsResponse.getGoods().stream().map(this::productFromGood).toList())
                .setPageSize(goodsResponse.getPagination().getSize())
                .setPageNo(goodsResponse.getPagination().getNumber())
                .setTotalElements(goodsResponse.getPagination().getTotalCount())
                .setHasMore(goodsResponse.getPagination().isHasMore())
                .setTotalPages(goodsResponse.getPagination().getTotalPages());
    }

    private ProductDto productFromGood(Good good) {
        Pattern amountPattern = Pattern.compile("(\\d+(мл|л|кг|шт|г))");
        Pattern measurementUnitPattern = Pattern.compile("(мл|л|кг|шт|г)");
        Matcher amountMatcher = amountPattern.matcher(good.getName());
        String name;
        double amount = 1d;
        String measurementUnit = "шт";
        String tmpAmount = "1шт";
        if (amountMatcher.find()) {
            name = good.getName().substring(0, amountMatcher.start());
            tmpAmount = good.getName().substring(amountMatcher.start(), amountMatcher.end());
        } else {
            name = good.getName();
            tmpAmount = (good.getUnitValue() == null || good.getUnitValue().isBlank())
                    ? tmpAmount : good.getUnitValue();
        }
        Matcher muMatcher = measurementUnitPattern.matcher(tmpAmount);
        if (muMatcher.find()) {
            amount = Double.parseDouble(tmpAmount.substring(0, muMatcher.start()).replaceAll(",", "."));
            measurementUnit = tmpAmount.substring(muMatcher.start(), muMatcher.end());
        }
        return new ProductDto()
                .setInShopId(good.getId())
                .setPrice(Double.valueOf(good.getOffers().get(0).getPrice().replace(",", ".")))
                .setShop(shop)
                .setAmount(amount)
                .setName(name.trim())
                .setMeasurementUnit(measurementUnit);
    }

}
