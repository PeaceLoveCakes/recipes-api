package ru.klingenberg.products_api.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.klingenberg.products_api.dto.ProductDto;
import ru.klingenberg.products_api.service.ProductService;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "products", groupId = "products", containerFactory = "productListener")
    public void listen(ProductDto product){
        productService.save(product);
    }

}
