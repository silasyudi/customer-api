package silas.yudi.entity.converter;

import org.springframework.stereotype.Service;
import silas.yudi.entity.dto.CustomerDto;
import silas.yudi.entity.model.Customer;

@Service
public class CustomerConverter implements Converter<Customer, CustomerDto> {

    @Override
    public CustomerDto toDto(Customer model) {
        CustomerDto dto = new CustomerDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setBirty(model.getBirty());

        return dto;
    }

    @Override
    public Customer toModel(CustomerDto dto) {
        Customer client = new Customer();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setBirty(dto.getBirty());

        return client;
    }
}
