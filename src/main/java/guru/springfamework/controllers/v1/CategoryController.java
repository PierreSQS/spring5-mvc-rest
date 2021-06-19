package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryServ;

    public CategoryController(CategoryService categoryServ) {
        this.categoryServ = categoryServ;
    }

    @GetMapping
    public List<CategoryDTO> getCategories() {
        return categoryServ.getAllCategories();
    }

    @GetMapping("{name}")
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryServ.getCategoryByName(name);
    }
}
