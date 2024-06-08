package ru.klingenberg.goods_collector;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.klingenberg.goods_collector.DTO.PageableResponse;
import ru.klingenberg.goods_collector.DTO.ProductDto;
import ru.klingenberg.goods_collector.kafka.KafkaProducer;
import ru.klingenberg.goods_collector.service.MagnitService;

@Component
@RequiredArgsConstructor
public class CollectGoodsSchedule {

    private final MagnitService magnitService;
    private final KafkaProducer kafkaProducer;

    @PostConstruct
    public void onStartup() {
        collectGoodsSchedule();
    }

    @Scheduled(cron = "${cron}")
    private void collectGoodsSchedule(){
        collectMagnitGoods();
    }

    private void collectMagnitGoods(){
        PageableResponse<ProductDto> products;
        int pageNo = 1;
        do {
            products = magnitService.findAll(pageNo++, 36);
            products.getElements().forEach(kafkaProducer::sendProduct);
        } while (products.isHasMore());
    }

}
