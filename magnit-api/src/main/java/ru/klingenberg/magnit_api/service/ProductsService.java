package ru.klingenberg.magnit_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.klingenberg.magnit_api.DTO.ShopProducts;
import ru.klingenberg.magnit_api.DTO.ProductDto;
import ru.klingenberg.magnit_api.DTO.goods.response.Good;
import ru.klingenberg.magnit_api.DTO.goods.response.GoodsResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductsService {

    @Value("${magnit.url}")
    private String url;

    private String shop = "Магнит";

    public ShopProducts shopProductsFromGoodsResponse(GoodsResponse goodsResponse){
        return new ShopProducts()
                .setElements(goodsResponse.getGoods().stream().map(this::productFromGood).toList())
                .setShopName(shop)
                .setHasMore(goodsResponse.getPagination().isHasMore());
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
                .setShopName(shop)
                .setAmount(amount)
                .setName(name.trim())
                .setMeasurementUnit(measurementUnit);
    }

}
