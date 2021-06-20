package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
