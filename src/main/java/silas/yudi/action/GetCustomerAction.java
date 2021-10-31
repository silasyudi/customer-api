package silas.yudi.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.service.CustomerService;

import java.util.List;

@RestController
public class GetCustomerAction {

    private final CustomerService service;

    @Autowired
    public GetCustomerAction(CustomerService service) {
        this.service = service;
    }

    @GetMapping("get/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAll(Pageable pageable) {
        return service.getAllCustomer(pageable);
    }

    @GetMapping("get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto get(@PathVariable Long id) {
        return service.getCustomer(id);
    }

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> get(Pageable pageable, @RequestBody CustomerDto example) {
        if (example.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter at least one field for search.");
        }

        return service.getCustomer(pageable, example);
    }
}
