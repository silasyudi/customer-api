package silas.yudi.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.service.CustomerService;

@RestController
public class DeleteCustomerAction {

    private final CustomerService service;

    @Autowired
    public DeleteCustomerAction(CustomerService service) {
        this.service = service;
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto delete(@PathVariable Long id) {
        return service.deleteCustomer(id);
    }
}
