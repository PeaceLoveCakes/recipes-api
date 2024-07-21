package ru.klingenberg.magnit_api.schedule;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.klingenberg.magnit_api.DTO.PageableResponse;
import ru.klingenberg.magnit_api.DTO.ProductDto;
import ru.klingenberg.magnit_api.kafka.KafkaProducer;
import ru.klingenberg.magnit_api.service.GoodsService;
import ru.klingenberg.magnit_api.service.ProductsService;


@Component
@RequiredArgsConstructor
public class CollectGoodsSchedule {

    private final GoodsService goodsService;
    private final ProductsService productsService;
    private final KafkaProducer kafkaProducer;

    @PostConstruct
    public void onStartup() {
        sendGoodsSchedule();
    }

    @Scheduled(cron = "${cron}")
    private void sendGoodsSchedule(){
        PageableResponse<ProductDto> products;
        int pageNo = 1;
        do {
            products = productsService.PageableResponseFromGoodsResponse(
                    goodsService.getGoods(pageNo++, 36));
            products.getElements().forEach(kafkaProducer::sendProduct);
        } while (products.isHasMore());
    }

}
