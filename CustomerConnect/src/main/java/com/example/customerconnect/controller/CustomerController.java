package com.example.customerconnect.controller;


import com.example.customerconnect.data.dto.CustomerDto;
import com.example.customerconnect.login.Login;
import com.example.customerconnect.service.CustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomersService customersService;
    private final RestTemplate restTemplate;

    private Login user(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Login> loginResponse
                = restTemplate.exchange("http://guardWay/parse-token", HttpMethod.GET, entity, Login.class);
        Login login = loginResponse.getBody();
        return login;
    }


    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto, @RequestHeader("Authorization") String token) {
        Login user = user(token);
        return ResponseEntity.ok(customersService.addCustomer(customerDto, user));
    }


    @PutMapping("/updateCustomer")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto, @RequestHeader("Authorization") String token) {
        Login user = user(token);
        return ResponseEntity.ok(customersService.updateCustomer(customerDto, user.uuid()));
    }

    @DeleteMapping("/deleteCustomer")
    public void deleteCustomer(@RequestHeader("Authorization") String token) {
        Login user = user(token);
        customersService.deleteCustomer(user.uuid());
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customersService.getAllCustomers());
    }

    @GetMapping("/getOneCustomer")
    public ResponseEntity<CustomerDto> getOneCustomer(@RequestHeader("Authorization") String token) {
        Login user = user(token);
        return ResponseEntity.ok(customersService.getOneCustomer(user.uuid()));

    }

}
