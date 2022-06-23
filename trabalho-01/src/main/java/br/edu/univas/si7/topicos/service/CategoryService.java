package br.edu.univas.si7.topicos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.edu.univas.si7.topicos.domain.Category;
import br.edu.univas.si7.topicos.domain.dto.CategoryDTO;
import br.edu.univas.si7.topicos.repositories.CategoryRepository;
import br.edu.univas.si7.topicos.support.exceptions.InvalidDataException;
import br.edu.univas.si7.topicos.support.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Category findById(Integer id) {
        Optional<Category> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Category " + id + " not found."));
    }

    public Integer createCategory(Category category) {
        repo.save(category);
        return category.getId();
    }

    public void updateCategory(Category category, Integer id) {
        if (id == null || category == null || id.equals(category.getId())) {
            throw new InvalidDataException("Invalid category id.");
        }
        Category existingObj = findById(id);
        updateData(existingObj, category);
        repo.save(category);
    }

    public void deleteCategory(Integer id) {
        if (id == null) {
            throw new InvalidDataException("Category id can not be null.");
        }
        Category cat = findById(id);
        try {
            repo.delete(cat);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Can not delete a category with products.");
        }
    }
    
    public Category getOrCreateCategory(String categoryName) {
    	Optional<Category> cat = repo.findByName(categoryName);
		if(cat.isPresent()) {
			return cat.get();
		}
		return repo.save(new Category(categoryName));
    }

    public List<Category> findAllWithOrder(String orderBy, String direction) {
        return repo.findAll(Sort.by(Direction.valueOf(direction), orderBy));
    }

    public Category toCategory(CategoryDTO category) {
        return new Category(category.getName());
    }

    private void updateData(Category existingObj, Category category) {
        existingObj.setName(category.getName());
    }

    public static Category toCategory(String categoryName) {
        return new Category(categoryName);
    }
}
