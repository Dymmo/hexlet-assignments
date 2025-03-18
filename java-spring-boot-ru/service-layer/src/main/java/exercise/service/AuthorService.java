package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository repository;
    @Autowired
    private AuthorMapper mapper;

    public List<AuthorDTO> get() {
        return repository.findAll().stream().map(mapper::map).toList();
    }
    public AuthorDTO get(long id) {
        return mapper.map(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found")));
    }
    public AuthorDTO post(AuthorCreateDTO dto) {
        return mapper.map(repository.save(mapper.map(dto)));
    }
    public AuthorDTO put(AuthorUpdateDTO dto, long id) {
        var book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        mapper.update(dto, book);
        return mapper.map(repository.save(book));
    }
    public void delete(long id) {
        repository.deleteById(id);
    }
    // END
}
