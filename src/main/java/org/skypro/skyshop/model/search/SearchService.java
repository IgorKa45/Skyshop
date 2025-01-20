package org.skypro.skyshop.model.search;

import org.skypro.skyshop.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }
    // Метод для поиска
    public Collection<SearchResult> search(String query){
        return storageService.getAllSearchables().stream() // Получаем все Searchable
                .filter(searchable -> searchable.getSearchTerm().toLowerCase().contains(query.toLowerCase())) // Фильтруем по запросу
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }

}
