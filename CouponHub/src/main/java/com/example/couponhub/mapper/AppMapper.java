package com.example.couponhub.mapper;


import com.example.couponhub.data.dto.CouponDto;
import com.example.couponhub.data.entity.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AppMapper {

    Coupon map(CouponDto couponDto);

    CouponDto map(Coupon coupon);
}

