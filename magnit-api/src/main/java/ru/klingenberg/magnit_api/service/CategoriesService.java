package ru.klingenberg.magnit_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.klingenberg.magnit_api.DTO.category.Category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    @Value("${magnit.url}")
    private String url;
    private final Consumer<HttpHeaders> httpHeaders;

    public List<Integer> getCategoriesIdsByNames(List<String> names){
        ResponseEntity<List<Category>> categoryResponseEntity = RestClient.builder()
                .baseUrl(url + "/v2/goods/categories?StoreCode=782510")
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .defaultHeaders(httpHeaders)
                .build()
                .get()
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Category>>() {});
        return Objects.requireNonNull(categoryResponseEntity.getBody())
                .stream()
                .filter(category -> names
                        .contains(category.getName()))
                .map(this::getAllId)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Integer> getDefaultCategoriesIds() {
        return getCategoriesIdsByNames(List.of(
                "Молоко, яйцо, сыр",
                "Овощи, фрукты ",
                "Птица, мясо",
                "Хлеб, выпечка",
                "Бакалея, соусы",
                "Консервы, мёд, варенье",
                "Сосиски, колбасы",
                "Рыба, морепродукты",
                "Замороженные продукты",
                "Здоровое питание"
        ));
    }

    private List<Integer> getAllId(Category category) {
        List<Integer> result = new ArrayList<>();
        if (category.getChildren().isEmpty()) {
            result.add(category.getId());
        } else {
            result.addAll(category.getChildren().stream()
                    .map(this::getAllId)
                    .flatMap(Collection::stream)
                    .toList());
        }
        return result;
    }
}
