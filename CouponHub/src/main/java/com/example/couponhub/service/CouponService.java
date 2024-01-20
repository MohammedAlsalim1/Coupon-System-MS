package com.example.couponhub.service;


import com.example.couponhub.data.dto.CouponDto;
import com.example.couponhub.data.entity.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CouponService {
    CouponDto addCoupon(CouponDto couponDto);

    void deleteCoupon(UUID couponUuid);

    List<CouponDto> getAllCoupons(UUID companyUuid);
    List<CouponDto> getAllCoupons();

    List<CouponDto> getAllCouponsOfCustomer(UUID customerUuid);

    CouponDto getOneCoupon(UUID couponUuid);

    CouponDto addCouponPurchase(UUID customerUuid, UUID couponUuid);
}
