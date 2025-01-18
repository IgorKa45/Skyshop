package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private String productName;
    private final UUID id;

    public Product(UUID id, String productName) {
        if ((productName == null)|| (productName.isBlank())){
            throw new IllegalArgumentException("Поле имя продукта не может быть пустым");
        }
        this.productName = productName;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public abstract int getProductCost();

    public String toString() {
        return getProductName() + ": " + getProductCost();
    }

    public boolean isSpecial() {
        return false; // По умолчанию товар не  специальный
    }

    @Override
    @JsonIgnore // Игнорируем поле при сериализации в JSON
    public String getSearchTerm() {
        return productName;
    }

    @Override
    @JsonIgnore // Игнорируем поле при сериализации в JSON
    public String getContentType() {
        return "Продукт";
    }

    @Override
    public String getName() {
        return productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

