package com.atrdev.projectbooks.controller;

import com.atrdev.projectbooks.entity.Book;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /*@GetMapping
    public List<Book> getBooks() {
        return books;
    }*/

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

    @GetMapping
    public List<Book> getBooksByCategory(@RequestParam(required = false) String category) {
        if (category == null) {
            return books;
        }

        /*List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;*/
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    /*@PostMapping
    public boolean addBook(@RequestBody Book book) {
        for (Book b : books) {
            if (book.getTitle().equalsIgnoreCase(b.getTitle())) {
                return false;
            }
        }
        books.add(book);
        return true;
    }*/

    @PostMapping
    public boolean addBook(@RequestBody Book book) {
        boolean isNewBook = books.stream()
                .noneMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle()));
        if (isNewBook) {
            books.add(book);
        }
        return isNewBook;
    }

    @PutMapping("/{title}")
    public boolean updateBook(@PathVariable String title, @RequestBody Book book) {
        Book existingBook = books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
        if (existingBook != null) {
            existingBook.setAuthor(book.getAuthor());
            existingBook.setCategory(book.getCategory());
            existingBook.setTitle(book.getTitle());
            return true;
        }
        return false;
    }

    @DeleteMapping("/{title}")
    public boolean deleteBook(@PathVariable String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

}
