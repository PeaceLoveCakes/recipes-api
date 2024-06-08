package ru.klingenberg.goods_collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.klingenberg.goods_collector.DTO.Good;
import ru.klingenberg.goods_collector.DTO.GoodsResponse;
import ru.klingenberg.goods_collector.DTO.PageableResponse;
import ru.klingenberg.goods_collector.DTO.ProductDto;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MagnitService {

    @Value("${magnit-api.url}")
    private String url;

    private String shop = "Магнит";

    public PageableResponse<ProductDto> findAll(Integer pageNo, Integer pageSize){
        GoodsResponse goodsResponse = Optional.ofNullable(RestClient.builder()
                .baseUrl(url + String.format("/goods/all?pageNo=%d&pageSize=%d", pageNo, pageSize))
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build()
                .get()
                .retrieve()
                .toEntity(GoodsResponse.class)
                .getBody())
                .orElseThrow();
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
        Pattern muPattern = Pattern.compile("(мл|л|кг|шт|г)");
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
        Matcher muMatcher = muPattern.matcher(tmpAmount);
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
