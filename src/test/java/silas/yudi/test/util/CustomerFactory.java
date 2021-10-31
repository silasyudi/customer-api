package silas.yudi.test.util;

import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.entity.model.Customer;

import java.time.LocalDate;

final public class CustomerFactory {

    public static Customer getModel() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test");
        customer.setBirty(LocalDate.of(2000, 1, 1));
        return customer;
    }

    public static CustomerDto getDto() {
        CustomerDto customer = new CustomerDto();
        customer.setId(1L);
        customer.setName("Test");
        customer.setBirty(LocalDate.of(2000, 1, 1));
        return customer;
    }
}
