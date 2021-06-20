package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTURL = "/api/v1/customers";
    private final CustomerRepository customerRepo;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        List<Customer> customersBylastName = customerRepo.findAll(Sort.by("lastName"));

        customersBylastName.forEach(cust -> cust.setCustomerUrl(CUSTURL+"/"+cust.getId()));
        customerRepo.saveAll(customersBylastName);

        return customersBylastName.stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }
}
