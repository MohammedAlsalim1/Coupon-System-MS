package com.example.customerconnect.service;


import com.example.customerconnect.data.dto.CustomerDto;
import com.example.customerconnect.login.Login;

import java.util.List;
import java.util.UUID;

public interface CustomersService {
    CustomerDto addCustomer(CustomerDto customerDto, Login login);

    CustomerDto updateCustomer(CustomerDto customerDto, UUID customerUuid);

    void deleteCustomer(UUID customerUuid);

    List<CustomerDto> getAllCustomers();

    CustomerDto getOneCustomer(UUID customerUuid);


}
