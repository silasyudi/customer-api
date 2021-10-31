package silas.yudi.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.service.CustomerService;

import static java.util.Objects.isNull;

@RestController
public class UpdateCustomerAction {

    private final CustomerService service;

    @Autowired
    public UpdateCustomerAction(CustomerService service) {
        this.service = service;
    }

    @PutMapping("put")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto put(@RequestBody CustomerDto customerDto) {
        if (isNull(customerDto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer ID cannot be empty.");
        }

        return service.updateCustomer(customerDto);
    }
}
