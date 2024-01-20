package com.example.couponhub.controller;

import com.example.couponhub.data.dto.CouponDto;
import com.example.couponhub.data.entity.Login;
import com.example.couponhub.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {
    public final CouponService couponService;
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


    @GetMapping("/getOneCoupon/{uuid}")
    public ResponseEntity<CouponDto> getOneCoupon(@PathVariable UUID uuid) {
        return ResponseEntity.ok(couponService.getOneCoupon(uuid));
    }

    @PostMapping("/creat")
    public ResponseEntity<CouponDto> creatCoupon(@RequestBody CouponDto couponDto) {
        return ResponseEntity.ok(couponService.addCoupon(couponDto));
    }

    @DeleteMapping("/delete/{uuid}")
    public void deleteCoupon(@PathVariable UUID uuid) {
        couponService.deleteCoupon(uuid);
    }

    @GetMapping("/company/all/{uuid}")
    public ResponseEntity<List<CouponDto>> getCoupons(@PathVariable UUID uuid) {
        return ResponseEntity.ok(couponService.getAllCoupons(uuid));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CouponDto>> getCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @GetMapping("/customers/all")
    public ResponseEntity<List<CouponDto>> getCouponsOfCustomers(@RequestHeader("Authorization") String token) {
        Login user = user(token);
        return ResponseEntity.ok(couponService.getAllCouponsOfCustomer(user.uuid()));
    }

    @PostMapping("/customer/buyCoupon/{uuid}")
    public ResponseEntity<CouponDto> addCouponPurchase(@RequestHeader("Authorization") String token, @PathVariable UUID uuid) {
        Login user = user(token);
        return ResponseEntity.ok(couponService.addCouponPurchase(user.uuid(), uuid));
    }
}
