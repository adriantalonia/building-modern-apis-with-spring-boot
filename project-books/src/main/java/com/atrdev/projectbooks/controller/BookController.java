package com.atrdev.projectbooks.controller;

import com.atrdev.projectbooks.model.dto.BookRequest;
import com.atrdev.projectbooks.model.entity.Book;
import jakarta.validation.constraints.Min;
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
                new Book(1, "Computer Science Pro", "Chad Darby", "Computer Science", 5),
                new Book(2, "Java Spring Master", "Eric Roby", "Computer Science", 5),
                new Book(3, "Why 1+1 Rocks", "Adil A.", "Math", 5),
                new Book(4, "How Bears Hibernate", "Bob B.", "Science", 2),
                new Book(5, "A Pirate's Treasure", "Curt C.", "History", 3),
                new Book(6, "Why 2+2 is Better", "Dan D.", "Math", 1)
        ));
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable @Min(value = 1) long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public boolean addBook(@RequestBody BookRequest bookRequest) {
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;
        Book book = convertToBook(bookRequest, id);
        return books.add(book);
    }

    @PutMapping("/{id}")
    public boolean updateBook(@PathVariable @Min(value = 1) long id, @RequestBody BookRequest bookRequest) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                Book book = convertToBook(bookRequest, id);
                books.set(i, book);
                return true;
            }
        }
        return false;
    }

    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable @Min(value = 1) long id) {
        return books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook(BookRequest bookRequest, long id) {
        return new Book(id, bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getCategory(), bookRequest.getRating());
    }

    /*@PutMapping("/{id}")
    public boolean updateBook(@PathVariable long id, @RequestBody Book book) {
        Book existingBook = books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
        if (existingBook != null) {
            existingBook.setAuthor(book.getAuthor());
            existingBook.setCategory(book.getCategory());
            existingBook.setTitle(book.getTitle());
            return true;
        }
        return false;
    }*/

    /*@PostMapping
    public boolean addBook(@RequestBody Book book) {
        boolean isNewBook = books.stream()
                .noneMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle()));
        if (isNewBook) {
            books.add(book);
        }
        return isNewBook;
    }*/

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

    /*@GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }*/

    /*@PutMapping("/{title}")
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
    }*/



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
}
