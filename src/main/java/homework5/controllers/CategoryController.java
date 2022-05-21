package homework5.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import homework5.dto.CategoryDto;
import homework5.entites.Category;
import homework5.exceptions.ResourceNotFoundException;
import homework5.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getOneById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find category with id: " + id));
        return new CategoryDto(category);
    }
}
