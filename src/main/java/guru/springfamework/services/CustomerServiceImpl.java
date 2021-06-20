package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepo.findAll(Sort.by("lastName")).stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }
}
