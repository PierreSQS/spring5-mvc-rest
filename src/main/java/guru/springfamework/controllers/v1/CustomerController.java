package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerSrv;

    public CustomerController(CustomerService customerSrv) {
        this.customerSrv = customerSrv;
    }


    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerSrv.getCustomers();
    }
}
