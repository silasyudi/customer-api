package silas.yudi.test.action;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.service.CustomerService;
import silas.yudi.test.util.CustomerFactory;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteCustomerActionTest {

    @MockBean
    private CustomerService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDelete() throws Exception {
        CustomerDto customer = CustomerFactory.getDto();

        when(service.deleteCustomer(1L)).thenReturn(CustomerFactory.getDto());

        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.birty").value("2000-01-01"))
                .andExpect(jsonPath("$.age").value(customer.getAge()))
        ;
    }
}
