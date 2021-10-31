package silas.yudi.test.action;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.service.CustomerService;
import silas.yudi.test.util.CustomerFactory;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateCustomerActionTest {

    @MockBean
    private CustomerService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdate() throws Exception {
        when(service.updateCustomer(isA(CustomerDto.class))).thenReturn(CustomerFactory.getDto());

        String content = "{\"id\":1, \"name\":\"Test\", \"birty\":\"2000-01-01\"}";

        mockMvc.perform(
                        put("/put")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.birty").value("2000-01-01"))
                .andExpect(jsonPath("$.age").value(CustomerFactory.getDto().getAge()))
        ;
    }

    @Test
    public void testUpdateWithoutId() throws Exception {
        String content = "{\"name\":\"Test\", \"birty\":\"2000-01-01\"}";

        mockMvc.perform(
                        put("/put")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
        ;
    }
}
