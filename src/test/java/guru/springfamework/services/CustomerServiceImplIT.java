package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
        // populating DB with data (this is an Integration Test)
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository);
        bootstrap.run();

        // Initializing the service manually (we just start the JPA-Data-Layer)
        customerService = new CustomerServiceImpl(customerMapper,customerRepository);
    }

    @Test
    public void patchCustomerByFirstName() {
        final String NEW_FN = "FN_TO_CHANGE";
        final String originalLastname = "Weston";
        final long ID_TO_FIND = getFirstElementID();

        // New Customer
        Customer newCustomer = new Customer();
        newCustomer.setFirstname(NEW_FN);

        // Customer to Update (existing in the DB)
        Customer toUpdateCustomer = null;

        // Check if customer exists
        Optional<Customer> optionalCustomer = customerRepository.findById(ID_TO_FIND);

        if (optionalCustomer.isPresent()){
            toUpdateCustomer = optionalCustomer.get();
            System.out.printf("######## found Customer: %s #########%n",toUpdateCustomer);
        } else {
            fail("Customer not found. Patch Impossible!!!");
        }


        // When
        final CustomerDTO customerDTO =
                customerService.patchCustomer(ID_TO_FIND, customerMapper.customerToCustomerDTO(newCustomer));

        // Then
        assertNotNull(customerDTO);
        assertThat("firstName not patched!!",
                toUpdateCustomer.getFirstname(),is(equalTo(NEW_FN)));

        assertThat("lastName should not be patched!!",
                toUpdateCustomer.getLastname(),is(equalTo(originalLastname)));

    }

    @Test
    public void patchCustomerByLastName() {
        final String NEW_LN = "LN_TO_CHANGE";
        final String originalFirstName = "Michale";
        final long ID_TO_FIND = getFirstElementID();

        // New Customer
        Customer newCustomer = new Customer();
        newCustomer.setLastname(NEW_LN);

        // Customer to Update (existing in the DB)
        Customer toUpdateCustomer = null;
        Optional<Customer> optionalCustomer = customerRepository.findById(ID_TO_FIND);

        // Check if customer exists
        if (optionalCustomer.isPresent()){
            toUpdateCustomer = optionalCustomer.get();
            System.out.printf("######## found Customer: %s #########%n",toUpdateCustomer);
        } else {
            fail("Customer not found. Patch Impossible!!!");
        }


        // When
        final CustomerDTO customerDTO =
                customerService.patchCustomer(ID_TO_FIND, customerMapper.customerToCustomerDTO(newCustomer));

        // Then
        assertNotNull(customerDTO);
        assertThat("lastName not patched!!",
                toUpdateCustomer.getLastname(),is(equalTo(NEW_LN)));

        assertThat("firstName should not be patched!!",
                toUpdateCustomer.getFirstname(),is(equalTo(originalFirstName)));

    }

    private Long getFirstElementID() {
        Long firstElmtID = customerRepository.findAll().get((0)).getId();

        System.out.printf("########## the first Element ID %d ##########%n",firstElmtID);
        return firstElmtID;
    }

}