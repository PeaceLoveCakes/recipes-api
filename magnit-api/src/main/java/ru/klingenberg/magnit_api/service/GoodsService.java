package ru.klingenberg.magnit_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.klingenberg.magnit_api.DTO.goods.Pagination;
import ru.klingenberg.magnit_api.DTO.goods.request.GoodsRequest;
import ru.klingenberg.magnit_api.DTO.goods.response.GoodsResponse;

import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class GoodsService {

    @Value("${magnit.url}")
    private String url;
    private final CategoriesService categoriesService;
    private final Consumer<HttpHeaders> httpHeaders;

    public GoodsResponse getGoods(Integer pageNo, Integer pageSize) {
        GoodsRequest goodsRequest = new GoodsRequest()
                .setCategoryIDs(categoriesService.getDefaultCategoriesIds());
        Pagination pagination = new Pagination()
                .setNumber(pageNo)
                .setSize(pageSize);
        goodsRequest.setPagination(pagination);
        return getGoods(goodsRequest);
    }

    public GoodsResponse getGoods(GoodsRequest goodsRequest) {
        return RestClient.builder()
                .baseUrl(url + "/v3/goods")
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .defaultHeaders(httpHeaders)
                .build()
                .post()
                .body(goodsRequest)
                .retrieve()
                .toEntity(GoodsResponse.class)
                .getBody();
    }

    public GoodsResponse suggest(String suggest){
        return RestClient.builder()
                .baseUrl(url + "v2/catalogs/suggest")
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .defaultHeaders(httpHeaders)
                .defaultUriVariables(Map.of(
                        "storeCode","782510",
                        "term", suggest,
                        "shopType", "1",
                        "onlyDiscount","false",
                        "includeForAdult","true"
                ))
                .build()
                .get()
                .retrieve()
                .toEntity(GoodsResponse.class)
                .getBody();
    }


}
