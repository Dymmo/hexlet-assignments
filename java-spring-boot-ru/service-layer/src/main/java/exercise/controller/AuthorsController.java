package exercise.controller;

import exercise.dto.*;
import exercise.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService service;

    // BEGIN
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> get() {
        return ResponseEntity.ok(service.get());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable long id) {
        return ResponseEntity.ok(service.get(id));
    }
    @PostMapping
    public ResponseEntity<AuthorDTO> post(@RequestBody AuthorCreateDTO dto) {
        return ResponseEntity.status(201).body(service.post(dto));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> put(@RequestBody AuthorUpdateDTO dto, @PathVariable long id) {
        return ResponseEntity.ok(service.put(dto, id));
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
    // END
}
