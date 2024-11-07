package org.example.library.controllers;


import org.example.library.models.Borrowing;
import org.example.library.repositories.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingRepository borrowingRepository;

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
        model.addAttribute("borrowing", new Borrowing());
        return "borrowings";
    }

    @PostMapping
    public String addBorrowing(@ModelAttribute Borrowing borrowing) {
        this.save(borrowing);
        return "redirect:/borrowings";
    }

}
