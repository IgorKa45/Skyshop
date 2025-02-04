package org.skypro.skyshop.controller;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.SearchService;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@RestController // Аннотация для создания REST-контроллера
@RequestMapping("/") // Базовый путь для всех методов контроллера
public class ShopController {
    private final StorageService storageService;
    private final SearchService searchService;
    private final ProductBasket productBasket;

    public ShopController(StorageService storageService, SearchService searchService, ProductBasket productBasket) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.productBasket = productBasket;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProduct();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticle();
    }

    @GetMapping("/search")
    public Collection<SearchResult> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    @PostMapping("/basket/add")
    public void addToBasket(@RequestParam UUID id) { // Новый метод
        productBasket.addProduct(id);
    }
    // Метод для получения содержимого корзины
    @GetMapping("/basket")
    public Map<UUID, Integer> getBasket() { // Новый метод
        return productBasket.getBasketContent();
    }
    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        Product product = storageService.getProductByIdOrThrow(id);//Условие для выбраасываеия исключения
            productBasket.addProduct(id); // Добавляем товар в корзину
            return "Продукт успешно добавлен"; //Сообщение об успешном добавлении

    }
}

