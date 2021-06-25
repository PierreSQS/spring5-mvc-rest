package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest extends AbstractRestControllerTest {

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
                .andExpect(jsonPath("$.customers[0].lastName").value("Mock1"))
                .andDo(print());
    }

    @Test
    public void getCustomerById() throws Exception {
        // Given
        Long mockID = 12L;
        String mockName = "Mock";
        String mockCustUrl = "api/v1/customers/"+mockID;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(mockID);
        customerDTO.setLastName("Mock");
        customerDTO.setCustomerUrl(mockCustUrl);
        when(customerSrvMock.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.lastName").value(mockName))
                .andExpect(jsonPath("$.customerUrl").value(mockCustUrl))
                .andDo(print());
    }


    @Test
    public void createCustomer() throws Exception{
        // Given
        final String custDTOMockUrl = "/api/v1/customers/10";
        CustomerDTO custDTOMock = new CustomerDTO();
        custDTOMock.setFirstName("Customer");
        custDTOMock.setLastName("To Create");

        CustomerDTO returnedCustDTO = new CustomerDTO();
        returnedCustDTO.setId(10L);
        returnedCustDTO.setFirstName(custDTOMock.getFirstName());
        returnedCustDTO.setLastName(custDTOMock.getLastName());
        returnedCustDTO.setCustomerUrl(custDTOMockUrl);

        String jsonContent = asJsonString(custDTOMock);

        when(customerSrvMock.createCustomer(custDTOMock)).thenReturn(returnedCustDTO);

        // When, Then
        mockMvc.perform(post("/api/v1/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent))
                .andExpect(jsonPath("$.lastName",equalTo("To Create")))
                .andExpect(jsonPath("$.customerUrl", equalTo(custDTOMockUrl)))
                .andDo(print());
    }

    @Test
    public void updateCustomerById() throws Exception {
        // Given
        CustomerDTO pCustDTO = new CustomerDTO();
        pCustDTO.setFirstName("Customer");
        pCustDTO.setLastName("To Update");

        CustomerDTO retCustDTO = new CustomerDTO();
        retCustDTO.setId(8L);
        retCustDTO.setFirstName("Customer");
        retCustDTO.setLastName("To Update");
        retCustDTO.setCustomerUrl("/api/v1/customers/8");
        when(customerSrvMock.updateCustomerByDTO(retCustDTO.getId(), pCustDTO)).thenReturn(retCustDTO);


        // When
        ResultActions actions = mockMvc.perform(put("/api/v1/customers/8")
                .content(asJsonString(pCustDTO))
                .contentType(MediaType.APPLICATION_JSON));

        // Then
       actions.andExpect(status().isOk())
               .andExpect(jsonPath("$.lastName").value("To Update"))
               .andExpect(jsonPath("$.customerUrl").value("/api/v1/customers/8"))
               .andDo(print());

        MockHttpServletRequest request = actions.andReturn().getRequest();
        request.setCharacterEncoding("UTF-8");

        MockHttpServletResponse response = actions.andReturn().getResponse();

        System.out.printf("%nthe Request: %s%n",request.getContentAsString());
        System.out.printf("the Response: %s%n%n",response.getContentAsString());

    }
}