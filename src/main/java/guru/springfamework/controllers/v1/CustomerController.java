package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerSrv;

    public CustomerController(CustomerService customerSrv) {
        this.customerSrv = customerSrv;
    }


    @GetMapping
    public CustomerListDTO getCustomers() {
        return new CustomerListDTO(customerSrv.getCustomers());
    }

    @GetMapping("{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerSrv.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO custDTO = customerSrv.createCustomer(customerDTO);
        return new ResponseEntity<>(custDTO, HttpStatus.CREATED);

    }
}
