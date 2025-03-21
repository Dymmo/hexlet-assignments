package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    // BEGIN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO post(@RequestBody ProductCreateDTO dto) {
        return mapper.map(productRepository.save(mapper.map(dto)));
    }
    @GetMapping(path = "/{id}")
    public ProductDTO getOne(@PathVariable long id) {
        return mapper.map(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found")));
    }
    @GetMapping
    public List<ProductDTO> get() {
        return productRepository.findAll().stream().map(product -> mapper.map(product)).toList();
    }
    @PutMapping(path = "/{id}")
    public ProductDTO put(@RequestBody ProductUpdateDTO dto, @PathVariable long id) {
        return mapper.map(
                productRepository.save(
                        mapper.map(
                                dto, productRepository.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("not found")))));
    }
    // END
}
