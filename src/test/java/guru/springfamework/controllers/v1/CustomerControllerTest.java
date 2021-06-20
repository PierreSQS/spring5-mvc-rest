package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerSrvMock;

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setUp() {

    }

    @Test
    public void getCustomers() throws Exception {
        // Given
        CustomerDTO custDTOMock1 = new CustomerDTO();
        custDTOMock1.setLastName("Mock1");

        CustomerDTO custDTOMock2 = new CustomerDTO();
        custDTOMock2.setLastName("Mock2");

        CustomerDTO custDTOMock3 = new CustomerDTO();
        custDTOMock3.setLastName("Mock3");

        List<CustomerDTO> customerDTOMocks = Arrays.asList(custDTOMock1,custDTOMock2, custDTOMock3);
        when(customerSrvMock.getCustomers()).thenReturn(customerDTOMocks);

        // When and Then
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Mock1"))
                .andDo(print());
    }
}