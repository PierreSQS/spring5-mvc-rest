package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerMVCTest {

    @MockBean
    CategoryService catServiceMock;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {

    }

    @Test
    public void testGetCategories() throws Exception {
        // Given
        CategoryDTO catDtoMock1 = new CategoryDTO();
        catDtoMock1.setId(1L);
        catDtoMock1.setName("Fruits");

        CategoryDTO catDtoMock2 = new CategoryDTO();
        catDtoMock2.setId(2L);
        catDtoMock2.setName("Fresh");
        when(catServiceMock.getAllCategories()).thenReturn(Arrays.asList(catDtoMock1,catDtoMock2));

        // When and Then
        mockMvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories[0].name").value("Fruits"))
                .andExpect(jsonPath("$.categories[1].name").value("Fresh"))
                .andDo(print());
    }

    @Test
    public void testGetCategoryByName() throws Exception {
        // Given
        String catDtoMockName = "FruitMock";

        CategoryDTO catDtoMock = new CategoryDTO();
        catDtoMock.setId(1L);
        catDtoMock.setName(catDtoMockName);

        when(catServiceMock.getCategoryByName(catDtoMockName)).thenReturn(catDtoMock);

        // When and Then
        mockMvc.perform(get("/api/v1/categories/FruitMock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(catDtoMockName))
                .andDo(print());
    }
}