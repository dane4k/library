package org.example.library.controllers;


import org.example.library.models.Book;
import org.example.library.models.Borrowing;
import org.example.library.models.Reader;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.BorrowingRepository;
import org.example.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public String printBorrowings(@RequestParam(required = false, defaultValue = "asc") String sort, Model model) {
        Sort sortOrder = Sort.by("borrowed");
        if ("desc".equals(sort)) {
            sortOrder = sortOrder.descending();
        }
        model.addAttribute("borrowings", borrowingRepository.findAll(sortOrder));
        model.addAttribute("readers", readerRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("borrowing", new Borrowing());
        model.addAttribute("direction", sort);
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

    @PostMapping("/return/{id}")
    public String returnBorrowing(@PathVariable Long id) {
        Borrowing borrowing = borrowingRepository.findById(id).orElse(null);
        if (borrowing != null) {
            borrowing.setBorrowed(false);
            borrowingRepository.save(borrowing);
        }
        return "redirect:/borrowings";
    }

    @GetMapping("/edit/{id}")
    public String editBorrowing(@PathVariable Long id, Model model) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid borrowing Id:" + id));
        model.addAttribute("borrowing", borrowing);
        model.addAttribute("readers", readerRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());

        return "edit_borrowing";
    }

    @PostMapping("/edit/{id}")
    public String updateBorrowing(@PathVariable Long id, @RequestParam Long readerId, @RequestParam Long bookId, @RequestParam(required = false) boolean borrowedFlag) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверное заимствование:" + id));
        Reader reader = readerRepository.findById(readerId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (reader != null && book != null) {
            borrowing.setReader(reader);
            borrowing.setBook(book);
            borrowing.setBorrowed(borrowedFlag);

            borrowingRepository.save(borrowing);
        }

        return "redirect:/borrowings";
    }

}

