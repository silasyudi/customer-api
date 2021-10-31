package silas.yudi.test.action;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.service.CustomerService;
import silas.yudi.test.util.CustomerFactory;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetCustomerActionTest {

    @MockBean
    private CustomerService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {
        when(service.getAllCustomer(isA(Pageable.class))).thenReturn(Lists.newArrayList(CustomerFactory.getDto()));

        mockMvc.perform(get("/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("Test"))
                .andExpect(jsonPath("$.[0].birty").value("2000-01-01"))
                .andExpect(jsonPath("$.[0].age").value(CustomerFactory.getDto().getAge()))
        ;
    }

    @Test
    public void testGetId() throws Exception {
        when(service.getCustomer(1L)).thenReturn(CustomerFactory.getDto());

        mockMvc.perform(get("/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.birty").value("2000-01-01"))
                .andExpect(jsonPath("$.age").value(CustomerFactory.getDto().getAge()))
        ;
    }

    @Test
    public void testGet() throws Exception {
        when(service.getCustomer(isA(Pageable.class), isA(CustomerDto.class)))
                .thenReturn(Lists.newArrayList(CustomerFactory.getDto()));

        String content = "{\"name\":\"Test\", \"birty\":\"2000-01-01\"}";

        mockMvc.perform(
                        get("/get")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("Test"))
                .andExpect(jsonPath("$.[0].birty").value("2000-01-01"))
                .andExpect(jsonPath("$.[0].age").value(CustomerFactory.getDto().getAge()))
        ;
    }

    @Test
    public void testGetEmpty() throws Exception {
        String content = "{}";

        mockMvc.perform(
                        get("/get")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
        ;
    }
}
