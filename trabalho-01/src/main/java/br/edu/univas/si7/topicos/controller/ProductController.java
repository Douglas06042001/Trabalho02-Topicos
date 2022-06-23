package br.edu.univas.si7.topicos.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.edu.univas.si7.topicos.domain.Product;
import br.edu.univas.si7.topicos.domain.dto.ProductDTO;
import br.edu.univas.si7.topicos.service.CategoryService;
import br.edu.univas.si7.topicos.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("")
    public List<ProductDTO> listAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> find(@PathVariable Integer id) {
        Product product = service.findById(id);
        return ResponseEntity.ok().body(new ProductDTO(product));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCategory(@Valid @RequestBody ProductDTO product) {
    	Category category = CategoryService.toCategory(product.getCategory());
        Integer newCategoryId = service.createProduct(service.toProduct(product, category));
        
        //não é obrigatório. É só para ficar em conformidade com o Restful
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategoryId).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@Valid @RequestBody ProductDTO product, @PathVariable Integer id) {
    	Category category = CategoryService.toCategory(product.getCategory());
    	service.updateProduct(service.toProduct(product, category), id);
    }

    //extra
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> findAllPage(
        @RequestParam(value="productName", defaultValue = "") String productName, 
        @RequestParam(value="categories", defaultValue = "") String categories, //categories separated by comma (,)
        @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
        @RequestParam(value="direction", defaultValue = "ASC") String direction) {

        List<String> categoryNames = new ArrayList<>();
        if(!categories.isBlank()) {
        	categoryNames.addAll(Arrays.asList(categories.split(",")).stream().map(c -> c).collect(Collectors.toList()));
        }
        
        String productNameDecoded;
        try {
            productNameDecoded = URLDecoder.decode(productName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            productNameDecoded = "";
        }

        List<ProductDTO> list = service
            .searchBy(productNameDecoded, categoryNames, orderBy, direction)
            .stream()
            .map(c -> new ProductDTO(c))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }
}
