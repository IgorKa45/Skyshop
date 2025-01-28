package org.skypro.skyshop.model.basket;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductBasket {
    // Мапа для хранения товаров и их количества
    private final Map<UUID, Integer> products;

    public ProductBasket() {
        this.products = new HashMap<>();
    }
    // Добавление товара в корзину
    public void addProduct(UUID id) {
        products.put(id, products.getOrDefault(id, 0) + 1);
    }
    // Получение содержимого корзины
    public Map<UUID, Integer> getBasketContent() {
        return Collections.unmodifiableMap(products);
    }

}

