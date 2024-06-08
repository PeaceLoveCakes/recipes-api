package ru.klingenberg.magnitgetgoods;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.klingenberg.magnitgetgoods.DTO.ProductDto;
import ru.klingenberg.magnitgetgoods.DTO.request.category.Category;
import ru.klingenberg.magnitgetgoods.DTO.request.goods.GoodsRequest;
import ru.klingenberg.magnitgetgoods.DTO.response.Good;
import ru.klingenberg.magnitgetgoods.DTO.response.GoodsResponse;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class GoodsSchedule {

    private final String url;
    private final Consumer<HttpHeaders> httpHeaders;

    public GoodsSchedule() throws UnsupportedEncodingException {
        url = "https://web-gateway.middle-api.magnit.ru/v3/goods";
        httpHeaders = httpHeaders();
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
    }

    @Scheduled(fixedRate = 100000000000L)
    private void getGoodsSchedule() {
        int num = 1;
        GoodsResponse goodsResponse;
        do {
            goodsResponse = getGoods(num++);
            sendGoods(Objects.requireNonNull(goodsResponse).getGoods());
        } while (goodsResponse.getPagination().isHasMore());
    }

    private void sendGoods(List<Good> goods) {
        RestClient.builder()
                .baseUrl("http://localhost:8080/addAll")
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build()
                .post().body(goods.stream().map(this::productDtoFromGood).collect(Collectors.toList()))
                .retrieve()
                .toBodilessEntity();
    }

    private GoodsResponse getGoods(Integer number) {
        return RestClient.builder()
                .baseUrl(url)
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .defaultHeaders(httpHeaders)
                .build()
                .post()
                .body(new GoodsRequest(number)
                        .setCategoryIDs(getRequiredCategoriesIds()))
                .retrieve()
                .toEntity(GoodsResponse.class)
                .getBody();
    }

    private List<Integer> getRequiredCategoriesIds() {
        ResponseEntity<List<Category>> categoryResponseEntity = RestClient.builder()
                .baseUrl("https://web-gateway.middle-api.magnit.ru/v2/goods/categories?StoreCode=782510")
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .defaultHeaders(httpHeaders)
                .build()
                .get()
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Category>>() {
                });
        return Objects.requireNonNull(categoryResponseEntity.getBody())
                .stream()
                .filter(category -> List.of(
                                "Молоко, яйцо, сыр",
                                "Фрукты, овощи",
                                "Мясо, птица",
                                "Хлеб, выпечка",
                                "Бакалея, соусы",
                                "Консервы, мёд, варенье",
                                "Сосиски, колбасы",
                                "Рыба, морепродукты",
                                "Замороженные продукты",
                                "Здоровое питание"
                                )
                        .contains(category.getName()))
                .map(this::getAllId)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public ProductDto productDtoFromGood(Good good) {
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
                .setPrice(Double.valueOf(good.getOffers().get(0).getPrice().replace(",", ".")))
                .setAmount(amount)
                .setName(name.trim())
                .setMeasurementUnit(measurementUnit);
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

    private Consumer<HttpHeaders> httpHeaders() {
        return httpHeaders -> {
            httpHeaders.set("Accept", "*/*");
            httpHeaders.set("Accept-Language", "en-US,en;q=0.9,ru;q=0.8");
            httpHeaders.set("Content-Type", "application/json");
            httpHeaders.set("x-app-version", "0.1.0");
            httpHeaders.set("x-client-name", "magnit");
            httpHeaders.set("x-device-id", "36s1expdgd");
            httpHeaders.set("x-device-platform", "Web");
            httpHeaders.set("x-device-tag", "disabled");
            httpHeaders.set("x-platform-version", "window.navigator.userAgent");
            httpHeaders.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64");
        };
    }
}
