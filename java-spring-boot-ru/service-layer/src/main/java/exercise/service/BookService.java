package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository repository;
    @Autowired
    private BookMapper mapper;

    public List<BookDTO> get() {
        return repository.findAll().stream().map(mapper::map).toList();
    }
    public BookDTO get(long id) {
        return mapper.map(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found")));
    }
    public BookDTO post(BookCreateDTO dto) {
        return mapper.map(repository.save(mapper.map(dto)));
    }
    public BookDTO put(BookUpdateDTO dto, long id) {
        var book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        mapper.update(dto, book);
        return mapper.map(repository.save(book));
    }
    public void delete(long id) {
        repository.deleteById(id);
    }
    // END
}
