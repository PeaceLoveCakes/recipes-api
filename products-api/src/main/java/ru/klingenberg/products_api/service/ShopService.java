package ru.klingenberg.products_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klingenberg.products_api.db.entity.Shop;
import ru.klingenberg.products_api.db.repository.ShopRepository;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public Shop getOrCreateByName(String name) {
        return shopRepository.findByNameIgnoreCase(name.trim())
                .orElseGet(() -> shopRepository.save(new Shop().setName(name)));
    }
}
