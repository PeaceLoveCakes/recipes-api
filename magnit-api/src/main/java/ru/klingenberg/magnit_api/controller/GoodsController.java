package ru.klingenberg.magnit_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.klingenberg.magnit_api.DTO.category.Category;
import ru.klingenberg.magnit_api.DTO.goods.request.GoodsRequest;
import ru.klingenberg.magnit_api.DTO.goods.request.Order;
import ru.klingenberg.magnit_api.DTO.goods.response.GoodsResponse;
import ru.klingenberg.magnit_api.service.GoodsService;

import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("goods")
@RequiredArgsConstructor
public class GoodsController {

    @Value("${magnit.url}")
    private String url;
    private final Consumer<HttpHeaders> httpHeaders;

    private final GoodsService goodsService;

    @GetMapping("all")
    public GoodsResponse goods(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "36") Integer pageSize){
        return goodsService.getGoods(pageNo, pageSize);
    }

    @GetMapping("suggest")
    public GoodsResponse suggest(@RequestParam String suggest){
        return goodsService.suggest(suggest);
    }

    @GetMapping("categories")
    public List<Category> categories(){
        return RestClient.builder()
                .baseUrl("https://web-gateway.middle-api.magnit.ru/v2/goods/categories?StoreCode=782510")
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .defaultHeaders(httpHeaders)
                .build()
                .get()
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Category>>() {
                }).getBody();
    }

}
