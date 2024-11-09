package org.example.library.controllers;


import org.example.library.models.Reader;
import org.example.library.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping
    public String printReaders(@RequestParam(required = false, defaultValue = "asc") String sort, Model model) {
        Sort sortOrder = Sort.by("firstName");
        if ("desc".equals(sort)) {
            sortOrder = sortOrder.descending();
        }
        model.addAttribute("readers", readerRepository.findAll(sortOrder));
        model.addAttribute("reader", new Reader());
        model.addAttribute("direction", sort);
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

    @GetMapping("/edit/{id}")
    public String editReader(@PathVariable Long id, Model model) {
        Reader reader = readerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid reader Id:" + id));
        model.addAttribute("reader", reader);
        return "edit_reader";
    }

    @PostMapping("/edit/{id}")
    public String updateReader(@PathVariable Long id, @ModelAttribute Reader reader) {
        reader.setId(id);
        readerRepository.save(reader);
        return "redirect:/readers";
    }
}