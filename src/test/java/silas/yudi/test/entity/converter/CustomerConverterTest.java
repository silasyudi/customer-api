package silas.yudi.test.entity.converter;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import silas.yudi.entity.converter.CustomerConverter;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.entity.model.Customer;
import silas.yudi.test.util.CustomerFactory;

import java.time.LocalDate;
import java.time.Period;

@SpringBootTest
public class CustomerConverterTest {

    @Test
    public void testToDto() {
        Customer model = CustomerFactory.getModel();
        CustomerDto dto = (new CustomerConverter()).toDto(model);

        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("Test", dto.getName());
        Assertions.assertEquals("2000-01-01", dto.getBirty().toString());
        Assertions.assertEquals(
                Period.between(dto.getBirty(), LocalDate.now()).getYears(),
                dto.getAge()
        );
    }

    @Test
    public void testToModel() {
        CustomerDto dto = CustomerFactory.getDto();
        Customer model = (new CustomerConverter()).toModel(dto);

        Assertions.assertEquals(1L, model.getId());
        Assertions.assertEquals("Test", model.getName());
        Assertions.assertEquals("2000-01-01", model.getBirty().toString());
    }
}
