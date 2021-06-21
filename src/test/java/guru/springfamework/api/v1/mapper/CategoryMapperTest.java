package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryMapperTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void categoryToCategoryDTO() {
        Category category = new Category();
        category.setName("Category");
        category.setId(5L);
        CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        assertThat(categoryDTO.getName()).isEqualTo("Category");
    }
}