package homework5.dto;

import homework5.entites.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class CategoryDto {
    private Long id;
    private String title;
    private List<ProductDto> products;

    public CategoryDto(Category c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.products = c.getProducts().stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
