package org.skypro.skyshop.model.basket;

import java.util.List;

public class UserBasket {
    private final List<BasketItem> items; // Список товаров в корзине
    private final int total; // Общая стоимость корзины

    public UserBasket(List<BasketItem> items) {
        this.items = items;
        // Вычисление общей стоимости корзины
        this.total = items.stream()
                .mapToInt(item -> item.getProduct().getProductCost() * item.getQuantity())
                .sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }
}
