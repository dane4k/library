package org.example.library.controllers;


import org.example.library.models.Book;
import org.example.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

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
    public String printBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("book", new Book());
        return "books";
    }

    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        this.save(book);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBookById(@PathVariable Long id) {
        this.deleteById(id);
        return "redirect:/books";
    }
}
