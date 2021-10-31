package silas.yudi.test.service;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;
import silas.yudi.entity.converter.CustomerConverter;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.entity.model.Customer;
import silas.yudi.repository.CustomerRepository;
import silas.yudi.service.CustomerService;
import silas.yudi.test.util.CustomerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Spy
    private CustomerConverter customerConverter = new CustomerConverter();
    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(CustomerFactory.getModel()));

        CustomerDto dto = customerService.getCustomer(1L);

        doAsserts(dto);
    }

    @Test
    public void testGetCustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> customerService.getCustomer(1L));
    }

    @Test
    public void testGetAllCustomer() {
        Customer customer = CustomerFactory.getModel();
        Page<Customer> page = new PageImpl<>(Lists.newArrayList(customer));
        when(customerRepository.findAll(isA(Pageable.class))).thenReturn(page);

        List<CustomerDto> customersDtos = customerService.getAllCustomer(mock(Pageable.class));

        Assertions.assertEquals(1, customersDtos.size());
        doAsserts(customersDtos.get(0));
    }

    @Test
    public void testGetEmptyCustomers() {
        Page<Customer> page = new PageImpl<>(Lists.emptyList());
        when(customerRepository.findAll(isA(Pageable.class))).thenReturn(page);

        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> customerService.getAllCustomer(mock(Pageable.class))
        );
    }

    @Test
    public void testGetCustomer() {
        Customer customer = CustomerFactory.getModel();
        Page<Customer> page = new PageImpl<>(Lists.newArrayList(customer));
        when(customerRepository.findAll(isA(Example.class), isA(Pageable.class))).thenReturn(page);

        List<CustomerDto> customersDtos = customerService.getCustomer(mock(Pageable.class), CustomerFactory.getDto());

        Assertions.assertEquals(1, customersDtos.size());
        doAsserts(customersDtos.get(0));
    }

    @Test
    public void testCreateCustomer() {
        when(customerRepository.save(isA(Customer.class))).thenReturn(CustomerFactory.getModel());

        CustomerDto dto = CustomerFactory.getDto();
        dto.setId(null);

        dto = customerService.createCustomer(dto);

        doAsserts(dto);
    }

    @Test
    public void testCreateCustomerWithId() {
        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> customerService.createCustomer(CustomerFactory.getDto())
        );
    }

    @Test
    public void testUpdateCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(isA(Customer.class))).thenReturn(CustomerFactory.getModel());

        CustomerDto dto = CustomerFactory.getDto();
        dto = customerService.updateCustomer(dto);

        doAsserts(dto);
    }

    @Test
    public void testUpdateCustomerNotExists() {
        when(customerRepository.existsById(1L)).thenReturn(false);
        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> customerService.updateCustomer(CustomerFactory.getDto())
        );
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(CustomerFactory.getModel()));

        CustomerDto dto = customerService.deleteCustomer(1L);

        doAsserts(dto);
    }

    private void doAsserts(CustomerDto dto) {
        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("Test", dto.getName());
        Assertions.assertEquals("2000-01-01", dto.getBirty().toString());
        Assertions.assertEquals(
                Period.between(dto.getBirty(), LocalDate.now()).getYears(),
                dto.getAge()
        );
    }
}
