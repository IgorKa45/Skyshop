package org.skypro.skyshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.SearchService;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.StorageService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SearchServiceTest {
    private StorageService storageServiceMock;
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        storageServiceMock = mock(StorageService.class);
        searchService = new SearchService(storageServiceMock);
    }
    //Проверяет, что если в StorageService нет объектов, поиск возвращает пустой список
    @Test
    void testSearch_NoObjectsInStorage() {
        when(storageServiceMock.getAllSearchables()).thenReturn(Collections.emptyList());

        List<SearchResult> results = (List<SearchResult>) searchService.search("ручка");

        assertEquals(0, results.size()); // Проверяем, что результат пуст
        verify(storageServiceMock, times(1)).getAllSearchables();
    }

    //Проверяет, что если объекты есть, но не подходят под запрос, поиск возвращает пустой список
    @Test
    void testSearch_NoMatchingObjects() {
        Searchable product = new SimpleProduct(UUID.randomUUID(), "Ластик", 50);
        Searchable article = new Article(UUID.randomUUID(), "Как выбрать канцтовары", "Описание выбора ручек и степлеров.");
        when(storageServiceMock.getAllSearchables()).thenReturn(List.of(product, article));

        List<SearchResult> results = (List<SearchResult>) searchService.search("компьютер");
        assertEquals(0, results.size());
        verify(storageServiceMock, times(1)).getAllSearchables();
    }

    //Ппроверяет, что если есть совпадение, поиск возвращает найденный объект
    @Test
    void testSearch_MatchingObjectFound() {
        Searchable product = new SimpleProduct(UUID.randomUUID(), "Ручка гелевая", 100);
        Searchable article = new Article(UUID.randomUUID(), "Как выбрать канцтовары", "Описание выбора ручек и степлеров.");
        when(storageServiceMock.getAllSearchables()).thenReturn(List.of(product, article));

        List<SearchResult> results = (List<SearchResult>) searchService.search("ручка");

        assertEquals(1, results.size());
        assertEquals("Ручка гелевая", results.get(0).getName());
        verify(storageServiceMock, times(1)).getAllSearchables();
    }
}
