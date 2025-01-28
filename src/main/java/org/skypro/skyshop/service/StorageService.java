package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage;
    private final Map<UUID, Article> articleStorage;

    public StorageService() {
        this.productStorage = new HashMap<>();
        this.articleStorage = new HashMap<>();
        fillStorageWithTestData(); // Заполняем хранилище
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productStorage.get(id));
    }
    // Метод для получения всех Searchable (продуктов и статей)
    public Collection<Searchable> getAllSearchables() {
        Collection<Searchable> searchables = new ArrayList<>();
        searchables.addAll(productStorage.values());
        searchables.addAll(articleStorage.values());
        return searchables;
    }

    // Метод для заполнения хранилища
    private void fillStorageWithTestData() {
        // Добавляем продукты
        productStorage.put(UUID.randomUUID(), new SimpleProduct(UUID.randomUUID(), "Корректор для текста", 218));
        productStorage.put(UUID.randomUUID(), new SimpleProduct(UUID.randomUUID(), "Ластик", 221));
        productStorage.put(UUID.randomUUID(), new DiscountedProduct(UUID.randomUUID(), "Клей", 120, 25));
        productStorage.put(UUID.randomUUID(), new DiscountedProduct(UUID.randomUUID(), "Cтеплер", 346, 15));
        productStorage.put(UUID.randomUUID(), new FixPriceProduct(UUID.randomUUID(), "Ручка"));
        productStorage.put(UUID.randomUUID(), new FixPriceProduct(UUID.randomUUID(), "Скобы для степлера"));
        // Добавляем статьи
        articleStorage.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Как выбрать канцтовары", "В статье описаны критерии выбора ручек, ластиков и степлеров."));
        articleStorage.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Советы по экономии", "Научитесь выбирать товары со скидкой."));
        articleStorage.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "История канцелярских принадлежностей", "Ручки и ластики имеют длинную и увлекательную историю."));
    }

    public Collection<Product> getAllProduct() {
        return productStorage.values();
    }

    public Collection<Article> getAllArticle() {
        return articleStorage.values();
    }

}
