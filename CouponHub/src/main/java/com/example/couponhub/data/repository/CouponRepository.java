package com.example.couponhub.data.repository;

import com.example.couponhub.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByUuid(UUID uuid);

    List<Coupon> findCouponsByCompanyID(UUID companyUuid);

}
