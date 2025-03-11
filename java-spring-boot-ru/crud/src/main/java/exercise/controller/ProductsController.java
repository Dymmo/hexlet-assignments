package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @GetMapping
    public List<ProductDTO> get() {
        return productRepository.findAll().stream().map(productMapper::map).toList();
    }
    @GetMapping(path = "/{id}")
    public ProductDTO get(@PathVariable long id) {
        return productMapper.map(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found")));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO post(@RequestBody ProductCreateDTO dto) {
        return productMapper.map(productRepository.save(productMapper.map(dto)));
    }
    @PutMapping(path = "/{id}")
    public ProductDTO put(@RequestBody ProductUpdateDTO dto, @PathVariable long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        productMapper.update(dto, product);
        return productMapper.map(productRepository.save(product));
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
    // END
}
