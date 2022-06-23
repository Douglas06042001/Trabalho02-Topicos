package br.edu.univas.si7.topicos.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.univas.si7.topicos.domain.Category;
import br.edu.univas.si7.topicos.domain.dto.CategoryDTO;
import br.edu.univas.si7.topicos.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = service.findAll()
            .stream()
            .map(c -> new CategoryDTO(c))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> find(@PathVariable Integer id) {
        Category cat = service.findById(id);
        return ResponseEntity.ok().body(new CategoryDTO(cat));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryDTO category) {
        Integer newCategoryId = service.createCategory(service.toCategory(category));
        
        //não é obrigatório. É só para ficar em conformidade com o Restful
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategoryId).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@Valid @RequestBody CategoryDTO category, @PathVariable Integer id) {
        service.updateCategory(service.toCategory(category), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
    }

    //extra
    @GetMapping("/ordered")
    public ResponseEntity<List<CategoryDTO>> findAllPage( 
        @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
        @RequestParam(value="direction", defaultValue = "ASC") String direction) {

        List<CategoryDTO> categories = service.findAllWithOrder(orderBy, direction)
            .stream().map(c -> new CategoryDTO(c))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(categories);
    }
}
