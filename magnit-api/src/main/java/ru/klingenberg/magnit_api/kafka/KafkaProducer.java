package ru.klingenberg.magnit_api.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.klingenberg.magnit_api.DTO.ProductDto;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, ProductDto> kafkaTemplate;

    public void sendProduct(ProductDto product){
        kafkaTemplate.send("products", product);
    }
}
