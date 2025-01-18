package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {
    private final String title;
    private final String content;
    private final UUID id;

    public Article(UUID id, String title, String content) {
        this.title = title; // Название статьи
        this.content = content; // Текст статьи
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title + "\n" + content;
    }

    @Override
    @JsonIgnore // Игнорируем поле при сериализации в JSON
    public String getSearchTerm() {
        return title + " " + content; // Искать можно по названию и тексту
    }

    @Override
    @JsonIgnore // Игнорируем поле при сериализации в JSON
    public String getContentType() {
        return "Статья";
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
