package com.example.customerconnect.mapper;
import com.example.customerconnect.data.dto.CustomerDto;
import com.example.customerconnect.data.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AppMapper {
   
    Customer map(CustomerDto customerDto);
    CustomerDto map(Customer customer);
}
