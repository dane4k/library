package org.example.library.controllers;


import org.example.library.models.Reader;
import org.example.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping
    public String printReaders(Model model) {
        model.addAttribute("readers", readerRepository.findAll());
        model.addAttribute("reader", new Reader());
        return "readers";
    }

    @PostMapping
    public String addReader(@ModelAttribute Reader reader) {
        readerRepository.save(reader);
        return "redirect:/readers";
    }

    @PostMapping("/delete/{id}")
    public String deleteReaderById(@PathVariable Long id) {
        readerRepository.deleteById(id);
        return "redirect:/readers";
    }
}