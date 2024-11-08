package org.example.library.controllers;


import org.example.library.models.Book;
import org.example.library.models.Borrowing;
import org.example.library.models.Reader;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.BorrowingRepository;
import org.example.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private ReaderController readerController;
    @Autowired
    private BookController bookController;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;

    public List<Borrowing> findAll() {
        return borrowingRepository.findAll();
    }

    public void save(Borrowing borrowing) {
        borrowingRepository.save(borrowing);
    }

    public void deleteById(Long id) {
        borrowingRepository.deleteById(id);
    }

    @GetMapping
    public String printBorrowings(Model model) {
        model.addAttribute("borrowings", borrowingRepository.findAll());
        model.addAttribute("readers", readerRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("borrowing", new Borrowing());
        return "borrowings";
    }

    @PostMapping
    public String addBorrowing(@RequestParam Long readerId, @RequestParam Long bookId) {
        Borrowing borrowing = new Borrowing();

        Reader reader = readerRepository.findById(readerId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (reader != null && book != null) {
            borrowing.setReader(reader);
            borrowing.setBook(book);
            borrowing.setBorrowed(true);
            borrowing.setBorrowDate(LocalDateTime.now());


            borrowingRepository.save(borrowing);
        }

        return "redirect:/borrowings";
    }

    @PostMapping("/delete/{id}")
    public String deleteBorrowingById(@PathVariable Long id) {
        borrowingRepository.deleteById(id);
        return "redirect:/borrowings";
    }
}

