package silas.yudi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import silas.yudi.entity.converter.CustomerConverter;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.entity.model.Customer;
import silas.yudi.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
    }

    public CustomerDto getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));

        return customerConverter.toDto(customer);
    }

    public List<CustomerDto> getAllCustomer(Pageable pageable) {
        return doTransformGetResult(customerRepository.findAll(pageable));
    }

    public List<CustomerDto> getCustomer(Pageable pageable, CustomerDto customerDto) {
        Customer customer = customerConverter.toModel(customerDto);

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnoreNullValues();

        Example<Customer> example = Example.of(customer, matcher);

        return doTransformGetResult(customerRepository.findAll(example, pageable));
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (!isNull(customerDto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot send ID on customer creation.");
        }

        return saveCustomer(customerDto);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        if (!customerRepository.existsById(customerDto.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found.");
        }

        return saveCustomer(customerDto);
    }

    public CustomerDto deleteCustomer(Long id) {
        CustomerDto customerDto = getCustomer(id);
        customerRepository.deleteById(id);
        return customerDto;
    }

    private List<CustomerDto> doTransformGetResult(Page<Customer> customers) {
        List<CustomerDto> customersDtos = new ArrayList<>();

        customers.forEach(c -> customersDtos.add(customerConverter.toDto(c)));

        if (customersDtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found.");
        }

        return customersDtos;
    }

    private CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = customerConverter.toModel(customerDto);
        customer = customerRepository.save(customer);
        return customerConverter.toDto(customer);
    }
}
