package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {
    static final String ORIGINAL_LN = "originalLN";
    static final String ORIGINAL_FN = "originalFN";
    static final String UPDATED_LN = "updatedLN";
    static final String UPDATED_FN = "updatedFN";
    static final long ID_TO_FIND = 1L;
    static final String CUST_DTO_URL= "/api/v1/customers/"+ID_TO_FIND;

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(2, customerDTOS.size());

    }

    @Test
    public void getCustomerById() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertEquals("Michale", customerDTO.getFirstname());
    }

    @Test
    public void createNewCustomer() {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
    }

    @Test
    public void patchCustomerByFirstName() {
        // THIS Customer-ID MUST BE THE SAME DURING THE TEST!!!!
        // ID of the found Customer in the DB that we want to UPDATE!!!
        final long ID = 1L;

        // Given
        // Found Customer to update
        Customer toUpdateCustMock = new Customer();
        toUpdateCustMock.setId(ID);
        toUpdateCustMock.setFirstname(ORIGINAL_FN);
        toUpdateCustMock.setLastname(ORIGINAL_LN);

        System.out.printf("%nfound customer to update BY FIRSTNAME: %s%n",toUpdateCustMock);

        // new Customer Mock
        Customer newCustomerMock = new Customer();
        newCustomerMock.setId(ID);
        newCustomerMock.setFirstname(UPDATED_FN);
        newCustomerMock.setLastname(toUpdateCustMock.getLastname());

        // CustomerDTO of the above updated customer
        CustomerDTO newCustDTOMock = new CustomerDTO();
        newCustDTOMock.setFirstname(newCustomerMock.getFirstname());
        newCustDTOMock.setCustomerUrl(CUST_DTO_URL);

        // search and get the customer to update
        when(customerRepository.findById(toUpdateCustMock.getId())).thenReturn(Optional.of(toUpdateCustMock));

        // save the updated customer
        when(customerRepository.save(newCustomerMock)).thenReturn(newCustomerMock);

        // when
        CustomerDTO newCustomerDTO = customerService.patchCustomer(ID, newCustDTOMock);

        System.out.printf("The returned DTO of the updated Customer By FIRSTNAME: %s%n",newCustomerDTO);

        // then
        // the Cust DTO FN = to Update Cust Mock FN (the to Update should have been updated)
        assertEquals(newCustomerDTO.getFirstname(), toUpdateCustMock.getFirstname());

        // to Update Cust Mock FN != Original FN -> (the to Update FN should != Original FN)
        assertThat(toUpdateCustMock.getFirstname(), not(equalTo(ORIGINAL_FN)));

        // the to Update LN should not change (we patch the FN)
        assertThat(toUpdateCustMock.getLastname(),equalTo(ORIGINAL_LN));

        assertThat(newCustomerDTO.getCustomerUrl(), equalTo(CUST_DTO_URL));
    }

    @Test
    public void patchCustomerByLastName() {
        // THIS Customer-ID MUST BE THE SAME DURING THE TEST!!!!
        // ID of the found Customer in the DB that we want to UPDATE!!!
        final long ID = 1L;

        // Given
        // Found Customer to update
        Customer toUpdateCust = new Customer();
        toUpdateCust.setId(ID);
        toUpdateCust.setLastname(ORIGINAL_LN);
        toUpdateCust.setFirstname(ORIGINAL_FN);

        System.out.printf("%nfound customer to update BY LASTNAME: %s%n",toUpdateCust);

        // updated Customer
        Customer updatedCust = new Customer();
        updatedCust.setId(ID);
        updatedCust.setLastname(UPDATED_LN);
        updatedCust.setFirstname(toUpdateCust.getFirstname());

        // CustomerDTO of the above updated customer
        CustomerDTO updatedCustDTO = new CustomerDTO();
        updatedCustDTO.setLastname(updatedCust.getLastname());

        // search and get the customer to update
        when(customerRepository.findById(toUpdateCust.getId())).thenReturn(Optional.of(toUpdateCust));

        // save the updated customer
        when(customerRepository.save(updatedCust)).thenReturn(updatedCust);

        // when
        CustomerDTO updatedCustomerDTO = customerService.patchCustomer(ID, updatedCustDTO);

        System.out.printf("The returned DTO of the updated Customer By LASTNAME: %s%n",updatedCustomerDTO);

        // then
        // LN updated
        assertEquals(updatedCustomerDTO.getLastname(), toUpdateCust.getLastname());

        // LN != Original LN (we patch the LN)
        assertThat(toUpdateCust.getLastname(), not(equalTo(ORIGINAL_LN)));

        // FN stays should not change (we patch the LN)
        assertThat(toUpdateCust.getFirstname(),equalTo(ORIGINAL_FN));

        assertThat(updatedCustomerDTO.getCustomerUrl(), equalTo(CUST_DTO_URL));
    }
}