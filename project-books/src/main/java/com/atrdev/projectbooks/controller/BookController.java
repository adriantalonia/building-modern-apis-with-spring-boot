package com.atrdev.projectbooks.controller;

import com.atrdev.projectbooks.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Book("Title one", "Author one", "Science"),
                new Book("TItle two", "Author two", "Math")
        ));
    }

    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    /*@GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }*/

    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
       return books.stream()
               .filter(book -> book.getTitle().equalsIgnoreCase(title))
               .findFirst()
               .orElse(null);
    }
}
