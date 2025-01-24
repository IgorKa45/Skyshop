package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID id) {
        Optional<Product> productOptional = storageService.getProductById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Товар с id " + id + " не найден");
        }
        productBasket.addProduct(id);
    }
    // Метод для получения корзины пользователя
    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketItems = productBasket.getBasketContent();

        // Преобразуем мапу в список BasketItem
        List<BasketItem> items = basketItems.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Товар с id " + productId + " не найден"));
                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}
