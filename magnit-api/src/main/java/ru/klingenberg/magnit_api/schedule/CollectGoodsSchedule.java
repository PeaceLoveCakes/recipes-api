package ru.klingenberg.magnit_api.schedule;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.klingenberg.magnit_api.DTO.ShopProducts;
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
        ShopProducts products;
        int pageNo = 1;
        do {
            products = productsService.shopProductsFromGoodsResponse(
                    goodsService.getGoods(pageNo++, 36));
            kafkaProducer.sendProducts(products);
        } while (products.isHasMore());
    }

}
