package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerMapperTest {

    private Customer customer;

    @Autowired
    private CustomerMapper custMapper ;

    @Before
    public void setUp() {
    }

    @Test
    public void customerToCustomerDTO() {
        customer = new Customer();
        customer.setFirstName("Pierrot");
        customer.setLastName("Mongonnam");

        CustomerDTO expectedCustDTO = new CustomerDTO();
        expectedCustDTO.setFirstName(customer.getFirstName());
        expectedCustDTO.setLastName(customer.getLastName());

        final CustomerDTO customerDTO = custMapper.customerToCustomerDTO(customer);

        assertThat(customerDTO.getFirstName()).isEqualTo(expectedCustDTO.getFirstName());
        assertThat(customerDTO.getLastName()).isEqualTo(expectedCustDTO.getLastName());
    }
}