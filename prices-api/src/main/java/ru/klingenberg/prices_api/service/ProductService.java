package ru.klingenberg.prices_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.klingenberg.prices_api.dto.ProductDto;

@Service
public class ProductService {

    @Value("${urls.products-api}")
    private String productsApiUtl;

    public ProductDto fetchProduct(String id){
        return RestClient.builder()
                .baseUrl(String.format("%s%s%s", productsApiUtl, "/product/", id))
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build()
                .get()
                .retrieve()
                .toEntity(ProductDto.class)
                .getBody();
    }

}
