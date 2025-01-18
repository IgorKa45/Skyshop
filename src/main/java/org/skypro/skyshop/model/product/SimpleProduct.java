package org.skypro.skyshop.model.product;

import java.util.UUID;

// Класс SimpleProduct, наследующий Product
public class SimpleProduct extends Product {
    private int productCost;

    public SimpleProduct(UUID id, String productName, int productCost) {
        super(id, productName);// Конструктор суперкласса
        if (productCost <= 0) {
            throw new IllegalArgumentException("Поле цена может принимать значения только больше нуля");
        }
        this.productCost = productCost;
    }

    @Override
    public int getProductCost() {
        return productCost;
    }
}