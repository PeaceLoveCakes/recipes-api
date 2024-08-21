package ru.klingenberg.magnit_api.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.klingenberg.magnit_api.DTO.ShopProducts;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, ShopProducts> kafkaTemplate;

    public void sendProducts(ShopProducts products){
        kafkaTemplate.send("products", products);
    }
}
