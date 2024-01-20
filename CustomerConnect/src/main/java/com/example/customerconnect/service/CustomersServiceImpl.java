package com.example.customerconnect.service;

import com.example.customerconnect.data.dto.CustomerDto;
import com.example.customerconnect.data.entity.Customer;
import com.example.customerconnect.data.repository.CustomerRepository;
import com.example.customerconnect.login.Login;
import com.example.customerconnect.mapper.AppMapper;
import com.example.customerconnect.service.ex.EmptyException;
import com.example.customerconnect.service.ex.NotExistException;
import com.example.customerconnect.service.ex.SameDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomersServiceImpl implements CustomersService {
    private final CustomerRepository customerRepository;
    private final AppMapper mapper;


    /**
     * Description: Adds a new customer to the system and saves it to the database if certain conditions are met.
     *
     * @param customerDto
     * @param login
     * @return A CustomerDto object representing the added customer.
     */
    @Override
    public CustomerDto addCustomer(CustomerDto customerDto, Login login) {

        if (customerDto != null) {
            customerDto.setEmail(login.username());
            customerDto.setUuid(login.uuid());
            if (customerRepository.findByEmail(customerDto.getEmail()).isEmpty()) {
                CustomerDto map = mapper.map(customerRepository.save(mapper.map(customerDto)));
                return map;
            } else
                throw new SameDetailsException("The e-mail not available");
        } else throw new EmptyException("Cannot add empty customer");
    }

    /**
     * Description: Updates an existing customer based on the provided customerDto and the UUID of the customer to be updated.
     *
     * @param customerDto
     * @param customerUuid
     * @return A CustomerDto object representing the updated customer.
     */
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, UUID customerUuid) {
        if (customerDto != null) {
            Customer customer = customerRepository.findByUuid(customerUuid).get();
            if (customerDto.getFirstName() != null)
                customer.setFirstName(customerDto.getFirstName());
            if (customerDto.getLastName() != null)
                customer.setLastName(customerDto.getLastName());
            return mapper.map(customerRepository.save(customer));
        } else throw new EmptyException("Cannot update empty customer");
    }

    /**
     * Description: Deletes a customer with the given UUID from the system and the database.
     *
     * @param customerUuid
     */
    @Override
    public void deleteCustomer(UUID customerUuid) {
        Optional<Customer> customerOptional = customerRepository.findByUuid(customerUuid);
        if (customerOptional.isPresent()) {
            customerRepository.delete(customerOptional.get());
        } else throw new NotExistException("the customer not exist");
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return A list of CustomerDto objects representing all customers.
     * @throws NotExistException if there are no customers.
     */
    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> Customers = customerRepository.findAll();
        if (Customers.isEmpty()) {
            throw new NotExistException("There are no customers.");
        }
        return Customers.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    /**
     * Description: Retrieves a single customer based on the provided UUID.
     *
     * @param customerUuid
     * @return An Optional containing the CustomerDto object representing the retrieved customer, or an empty Optional if no customer with the given UUID is found.
     */
    @Override
    public CustomerDto getOneCustomer(UUID customerUuid) {
        Optional<Customer> customerOptional = customerRepository.findByUuid(customerUuid);
        if (customerOptional.isPresent()) {
            return customerRepository.findByUuid(customerUuid).map(mapper::map).get();

        } else throw new NotExistException("the customer not exist");
    }
}
