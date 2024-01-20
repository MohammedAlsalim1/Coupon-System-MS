package com.example.customerconnect.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
public class CustomerDto {
    @JsonProperty("id")
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
}
