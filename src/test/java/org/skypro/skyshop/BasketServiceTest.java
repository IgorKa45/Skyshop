package org.skypro.skyshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BasketServiceTest {
    @Mock
    private ProductBasket productBasketMock;
    @Mock
    private StorageService storageServiceMock;
    @InjectMocks
    private BasketService basketService;

    private UUID existingProductId;
    private Product existingProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existingProductId = UUID.randomUUID();
        existingProduct = new SimpleProduct(existingProductId, "Тестовый продукт", 100);
    }

//НЭ РАБОТАЕТ
    @Test
    void testAddNonExistentProductToBasket_ThrowsException() {
        when(storageServiceMock.getProductByIdOrThrow(existingProductId))
                .thenThrow(new NoSuchProductException("Товар не найден"));

        assertThrows(NoSuchProductException.class, () -> basketService.addProductToBasket(existingProductId));

        verify(productBasketMock, never()).addProduct(any());
    }

    @Test
    void testAddExistingProductToBasket_CallsProductBasketAddProduct() {
        when(storageServiceMock.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));

        basketService.addProductToBasket(existingProductId);

        verify(productBasketMock, times(1)).addProduct(existingProductId);
    }

    @Test
    void testGetUserBasket_ReturnsEmpty_WhenNoProductsInBasket() {
        when(productBasketMock.getBasketContent()).thenReturn(Collections.emptyMap());

        UserBasket userBasket = basketService.getUserBasket();

        assertTrue(userBasket.getItems().isEmpty());
    }

    @Test
    void testGetUserBasket_ReturnsCorrectItems_WhenProductsAreInBasket() {
        when(productBasketMock.getBasketContent()).thenReturn(Map.of(existingProductId, 2));
        when(storageServiceMock.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));

        UserBasket userBasket = basketService.getUserBasket();

        assertEquals(1, userBasket.getItems().size());
        BasketItem basketItem = userBasket.getItems().get(0);
        assertEquals(existingProduct, basketItem.getProduct());
        assertEquals(2, basketItem.getQuantity());
    }
}
