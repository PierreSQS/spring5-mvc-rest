package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepoMock;

    private CustomerService customerSrv;

    @Before
    public void setUp() {
        customerSrv = new CustomerServiceImpl(customerRepoMock, CustomerMapper.INSTANCE);
    }

    @Test
    public void getCustomers() {
        // Given
        Customer custMock1 = new Customer();
        custMock1.setLastName("Customer Mock1");
        
        Customer custMock2 = new Customer();
        custMock2.setLastName("Customer Mock2");
        
        Customer custMock3 = new Customer();
        custMock3.setLastName("Customer Mock3");
        custMock3.setId(3L);

        when(customerRepoMock.findAll(Sort.by("lastName"))).thenReturn(Arrays.asList(custMock1,custMock2,custMock3));

        final List<CustomerDTO> customerDTOs = customerSrv.getCustomers();

        assertThat(customerDTOs).hasSize(3);
        assertThat(customerDTOs.get(0).getLastName()).isEqualTo("Customer Mock1");
        assertThat(customerDTOs.get(2).getCustomerUrl()).isEqualTo("/api/v1/customers/3");

    }
}