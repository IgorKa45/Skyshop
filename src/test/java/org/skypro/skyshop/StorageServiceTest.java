package org.skypro.skyshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.StorageService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class StorageServiceTest {
    private StorageService storageService;
    private UUID existingProductId;
    private Product existingProduct;

    @BeforeEach
    void setUp() {
        storageService = new StorageService();
        existingProductId = UUID.randomUUID();
        existingProduct = new SimpleProduct(existingProductId, "Тестовый продукт", 100);

        storageService.getProductStorage().put(existingProductId, existingProduct);
    }
    // Проверяет, что можно получить существующий товар по ID
    @Test
    void testGetProductById_ProductExists() {
        assertTrue(storageService.getProductById(existingProductId).isPresent());
        assertEquals("Тестовый продукт", storageService.getProductById(existingProductId).get().getName());
    }

    // Проверяет, что если товара с таким ID нет, метод вернёт пустой Optional
    @Test
    void testGetProductById_ProductNotFound() {
        UUID randomId = UUID.randomUUID();
        assertFalse(storageService.getProductById(randomId).isPresent());
    }

    // Проверяет, что `getProductByIdOrThrow()` возвращает существующий товар
    @Test
    void testGetProductByIdOrThrow_ProductExists() {
        Product product = storageService.getProductByIdOrThrow(existingProductId);
        assertEquals("Тестовый продукт", product.getName());
    }

    // Проверяет, что `getProductByIdOrThrow()`выбрасывает исключение, если товара нет
    @Test
    void testGetProductByIdOrThrow_ProductNotFound_ThrowsException() {
        UUID randomId = UUID.randomUUID();
        assertThrows(NoSuchProductException.class, () -> storageService.getProductByIdOrThrow(randomId));
    }

    // Проверяет, что список товаров не пуст после инициализации
    @Test
    void testGetAllProduct_ReturnsList() {
        assertFalse(storageService.getAllProduct().isEmpty());
    }
}

