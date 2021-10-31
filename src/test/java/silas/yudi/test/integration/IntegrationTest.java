package silas.yudi.test.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import silas.yudi.test.util.CustomerFactory;

import java.time.LocalDate;
import java.time.Period;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        for (int i = 0; i < 5; i++) {
            String content = "{\"name\":\"Test\", \"birty\":\"2000-01-01\"}";

            mockMvc.perform(
                            post("/post")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(i + 1))
                    .andExpect(jsonPath("$.name").value("Test"))
                    .andExpect(jsonPath("$.birty").value("2000-01-01"))
                    .andExpect(jsonPath("$.age").value(CustomerFactory.getDto().getAge()))
            ;
        }
    }

    @Test
    @Order(2)
    public void testUpdate() throws Exception {
        String changeName = "{\"id\":1, \"name\":\"CHANGE NAME\", \"birty\":\"2000-01-01\"}";
        String changeBirty = "{\"id\":2, \"name\":\"Test\", \"birty\":\"2002-02-02\"}";

        mockMvc.perform(
                        put("/put")
                                .content(changeName)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("CHANGE NAME"))
                .andExpect(jsonPath("$.birty").value("2000-01-01"))
                .andExpect(jsonPath("$.age").value(CustomerFactory.getDto().getAge()))
        ;

        mockMvc.perform(
                        put("/put")
                                .content(changeBirty)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.birty").value("2002-02-02"))
                .andExpect(jsonPath("$.age")
                        .value(
                                Period.between(LocalDate.of(2002, 2, 2), LocalDate.now()).getYears()
                        )
                )
        ;
    }

    @Test
    @Order(3)
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/delete/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.birty").value("2000-01-01"))
                .andExpect(jsonPath("$.age").value(CustomerFactory.getDto().getAge()))
        ;
    }

    @Test
    @Order(4)
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[1].id").value(2L))
        ;

        mockMvc.perform(get("/get/all?page=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(3L))
                .andExpect(jsonPath("$.[1].id").value(4L))
        ;

        mockMvc.perform(get("/get/all?size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[2].id").value(3L))
                .andExpect(jsonPath("$.[3].id").value(4L))
        ;
    }

    @Test
    @Order(5)
    public void testGetByName() throws Exception {
        String search = "{\"name\":\"CHANGE\"}";

        mockMvc.perform(
                        get("/get")
                                .content(search)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("CHANGE NAME"))
        ;
    }
}
