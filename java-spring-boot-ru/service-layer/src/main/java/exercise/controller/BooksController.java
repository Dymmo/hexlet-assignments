package exercise.controller;

import java.util.List;

import exercise.dto.*;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService service;

    // BEGIN
    @GetMapping
    public ResponseEntity<List<BookDTO>> get() {
        return ResponseEntity.ok(service.get());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable long id) {
        return ResponseEntity.ok(service.get(id));
    }
    @PostMapping
    public ResponseEntity<BookDTO> post(@RequestBody BookCreateDTO dto) {
        return ResponseEntity.status(201).body(service.post(dto));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<BookDTO> put(@RequestBody BookUpdateDTO dto, @PathVariable long id) {
        return ResponseEntity.ok(service.put(dto, id));
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
    // END
}
