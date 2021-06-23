package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTURL = "/api/v1/customers/";
    private final CustomerRepository customerRepo;
    private final CustomerMapper customerMapper;
    private final Function<Customer, CustomerDTO> custToCustDTOFunct;

    public CustomerServiceImpl(CustomerRepository customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
        this.custToCustDTOFunct = cust -> {
            CustomerDTO custDTO = customerMapper.customerToCustomerDTO(cust);
            custDTO.setCustomerUrl(CUSTURL + cust.getId());
            return custDTO;
        };
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        List<Customer> customersBylastName = customerRepo.findAll(Sort.by("lastName"));

        return customersBylastName.stream()
                .map(custToCustDTOFunct)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(-1L);
        customerDTO.setFirstName("Customer");
        customerDTO.setLastName("Not Found");

        return customerRepo.findById(id)
                .map(custToCustDTOFunct).orElse(customerDTO);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer savedCustomer = customerRepo.save(customerMapper.customerDTOToCustomer(customerDTO));
        log.info("the created Customer: {}", savedCustomer);

        CustomerDTO createdDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        createdDTO.setCustomerUrl(CUSTURL+savedCustomer.getId());

        log.info("the created CustomerDTO {}",createdDTO);
        return createdDTO;
    }
}
