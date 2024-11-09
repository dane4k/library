package org.example.library.controllers;


import org.example.library.models.Book;
import org.example.library.models.Category;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @GetMapping
    public String printBooks(Model model, @RequestParam(defaultValue = "title") String sort,
                             @RequestParam(defaultValue = "asc") String direction) {
        List<Book> books;
        if ("desc".equalsIgnoreCase(direction)) {
            books = bookRepository.findAll(Sort.by(Sort.Direction.DESC, sort));
        } else {
            books = bookRepository.findAll(Sort.by(Sort.Direction.ASC, sort));
        }

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categories);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);

        return "books";
    }

    @PostMapping
    public String addBook(@ModelAttribute Book book, @RequestParam Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            book.setCategory(category);
        }
        this.save(book);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBookById(@PathVariable Long id) {
        this.deleteById(id);
        return "redirect:/books";
    }
}
