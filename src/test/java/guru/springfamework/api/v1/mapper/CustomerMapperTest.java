package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMapperTest {

    private Customer customer;

    private CustomerMapper custMapper ;

    @Before
    public void setUp() {
        custMapper = CustomerMapper.INSTANCE;
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