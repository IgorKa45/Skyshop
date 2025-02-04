package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
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
    public Product getProductByIdOrThrow(UUID id) {
        return getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Товар с id " + id + " не найден"));
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
        UUID id1 = UUID.randomUUID();
        productStorage.put(id1, new SimpleProduct(id1, "Корректор для текста", 218));
        UUID id2 = UUID.randomUUID();
        productStorage.put(id2, new SimpleProduct(id2, "Ластик", 221));
        UUID id3 = UUID.randomUUID();
        productStorage.put(id3, new DiscountedProduct(id3, "Клей", 120, 25));
        UUID id4 = UUID.randomUUID();
        productStorage.put(id4, new DiscountedProduct(id4, "Cтеплер", 346, 15));
        UUID id5 = UUID.randomUUID();
        productStorage.put(id5, new FixPriceProduct(id5, "Ручка"));
        UUID id6 = UUID.randomUUID();
        productStorage.put(id6, new FixPriceProduct(id6, "Скобы для степлера"));
        // Добавляем статьи
        UUID articleId1 = UUID.randomUUID();
        articleStorage.put(articleId1, new Article(articleId1, "Как выбрать канцтовары", "В статье описаны критерии выбора ручек, ластиков и степлеров."));
        UUID articleId2 = UUID.randomUUID();
        articleStorage.put(articleId2, new Article(articleId2, "Советы по экономии", "Научитесь выбирать товары со скидкой."));
        UUID articleId3 = UUID.randomUUID();
        articleStorage.put(articleId3, new Article(articleId3, "История канцелярских принадлежностей", "Ручки и ластики имеют длинную и увлекательную историю."));
    }

    public Collection<Product> getAllProduct() {
        return productStorage.values();
    }

    public Collection<Article> getAllArticle() {
        return articleStorage.values();
    }

}
