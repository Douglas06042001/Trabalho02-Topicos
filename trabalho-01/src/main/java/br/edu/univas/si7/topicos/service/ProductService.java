package br.edu.univas.si7.topicos.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.edu.univas.si7.topicos.domain.Category;
import br.edu.univas.si7.topicos.domain.Product;
import br.edu.univas.si7.topicos.domain.dto.ProductDTO;
import br.edu.univas.si7.topicos.repositories.CategoryRepository;
import br.edu.univas.si7.topicos.repositories.ProductRepository;
import br.edu.univas.si7.topicos.support.exceptions.InvalidDataException;
import br.edu.univas.si7.topicos.support.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
    private CategoryRepository categoryRepo;

	public List<ProductDTO> findAll() {
		return repo.findAll().stream().map(p -> new ProductDTO(p)).collect(Collectors.toList());
	}

	public Product findById(Integer id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Product " + id + " not found."));
	}

	public List<Product> searchBy(String name, List<String> categoryNames, String orderBy, String direction) {

		if(!categoryNames.isEmpty()) {
			List<Category> categories= categoryRepo.findByNameIn(categoryNames);
			return repo.findDistinctByNameContainingAndCategoryIn(
				name, categories,
				Sort.by(Direction.valueOf(direction), orderBy));
		} else {
			return repo.findDistinctByNameContaining(
					name, Sort.by(Direction.valueOf(direction), orderBy));
		}
	}
	
    public Integer createProduct(Product product) {
    	Category category = categoryService.getOrCreateCategory(product.getCategory().getName());
    	product.setCategory(category);
        repo.save(product);
        return product.getId();
    }
    
    public void updateProduct(Product product, Integer id) {
        if (id == null || product == null || id.equals(product.getId())) {
            throw new InvalidDataException("Invalid product id.");
        }
        Product existingObj = findById(id);
        updateData(existingObj, product);
        repo.save(product);
    }


    public Product toProduct(ProductDTO product, Category category) {
        return new Product(product.getName(), product.getPrice(), category);
    }
    
    private void updateData(Product existingObj, Product product) {
        existingObj.setName(product.getName());
        existingObj.setPrice(product.getPrice());
        existingObj.setCategory(product.getCategory());
    }
}
