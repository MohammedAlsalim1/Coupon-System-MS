package com.example.couponhub.data.dto;

import com.example.couponhub.data.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class CouponDto {
    @JsonProperty("id")
    private UUID uuid;
    @JsonProperty("company_id")
    private UUID companyID;
    private String title;
    private Category category;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
}
