package silas.yudi.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.service.CustomerService;

@RestController
public class NewCustomerAction {

    private final CustomerService service;

    @Autowired
    public NewCustomerAction(CustomerService service) {
        this.service = service;
    }

    @PostMapping("post")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto post(@RequestBody CustomerDto customerDto) {
        return service.createCustomer(customerDto);
    }
}
