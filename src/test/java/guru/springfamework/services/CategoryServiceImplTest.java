package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository catRepoMock;

    private CategoryService categorySrv;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categorySrv = new CategoryServiceImpl(catRepoMock, CategoryMapper.INSTANCE);
    }

    @Test
    public void getAllCategories() {
        // Given
        final String mockFreshName = "Fresh";
        final String mockFruitName = "Fruits";

        Category catMock1 = new Category();
        catMock1.setId(1L);
        catMock1.setName(mockFruitName);

        Category catMock2 = new Category();
        catMock2.setId(2L);
        catMock2.setName(mockFreshName);

        CategoryDTO catDtoMock1 = new CategoryDTO();
        catDtoMock1.setId(1L);
        catDtoMock1.setName(mockFruitName);

        CategoryDTO catDtoMock2 = new CategoryDTO();
        catDtoMock2.setId(2L);
        catDtoMock2.setName(mockFreshName);

        List<Category> categoriesMock = Arrays.asList(catMock1, catMock2);
        when(catRepoMock.findAll()).thenReturn(categoriesMock);

        // When
        List<CategoryDTO> allCategories = categorySrv.getAllCategories();
        System.out.printf("The Categories: %s",allCategories);

        // Then
        assertThat(allCategories).hasSize(2)
                                 .contains(catDtoMock1,catDtoMock2);
    }

    @Test
    public void getCategoryByName() {
        // Given
        String mockFruitName = "Fruits";

        Category catMock = new Category();
        catMock.setId(1L);
        catMock.setName(mockFruitName);
        Optional<Category> optCatMock = Optional.of(catMock);
        when(catRepoMock.findByName(anyString())).thenReturn(optCatMock);

        // When
        CategoryDTO fruit = categorySrv.getCategoryByName(mockFruitName);

        // Then
        assertThat(fruit.getName()).isEqualTo(mockFruitName);
        System.out.println("the found Fruit: "+fruit);

        verify(catRepoMock).findByName(any());
    }
}