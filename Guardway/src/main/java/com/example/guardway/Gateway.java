package com.example.guardway;

import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Gateway {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authForge-route-signUp", Gateway::signUp)
                .route("authForge-route-signIn", Gateway::signIn)
                .route("authForge-route-parseToken", Gateway::parseToken)
                .route("getCoupon", Gateway::getCoupon)
                .route("creatCoupon", Gateway::creatCoupon)
                .route("deleteCoupon", Gateway::deleteCoupon)
                .route("companyCoupons", Gateway::companyCoupons)
                .route("Coupons", Gateway::Coupons)
                .route("customerCoupons", Gateway::customerCoupons)
                .route("purchaseCoupon", Gateway::purchaseCoupon)
                .route("addCustomer", Gateway::addCustomer)
                .route("updateCustomer", Gateway::updateCustomer)
                .route("deleteCustomer", Gateway::deleteCustomer)
                .route("getAllCustomer", Gateway::getAllCustomer)
                .route("getOneCustomer", Gateway::getOneCustomer)
                .build();
    }

    @Bean
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    private static Buildable<Route> Coupons(PredicateSpec predicateSpec) {
        return predicateSpec.path("/coupons")
                .filters(f -> f.rewritePath("/coupons", "/api/all"))
                .uri("lb://coupon-hub");
    }

    private static Buildable<Route> getOneCustomer(PredicateSpec predicateSpec) {
        return predicateSpec.path("/get-customer")
                .filters(f -> f.rewritePath("/get-customer", "/api/getOneCustomer"))
                .uri("lb://customer-connect");
    }

    private static Buildable<Route> getAllCustomer(PredicateSpec predicateSpec) {
        return predicateSpec.path("/get-all")
                .filters(f -> f.rewritePath("/get-all", "/api/getAllCustomers"))
                .uri("lb://customer-connect");
    }

    private static Buildable<Route> deleteCustomer(PredicateSpec predicateSpec) {
        return predicateSpec.path("/delete")
                .filters(f -> f.rewritePath("/delete", "/api/deleteCustomer"))
                .uri("lb://customer-connect");

    }

    private static Buildable<Route> updateCustomer(PredicateSpec predicateSpec) {
        return predicateSpec.path("/update")
                .filters(f -> f.rewritePath("/update", "/api/updateCustomer"))
                .uri("lb://customer-connect");
    }

    private static Buildable<Route> addCustomer(PredicateSpec predicateSpec) {
        return predicateSpec.path("/add")
                .filters(f -> f.rewritePath("/add", "/api/addCustomer"))
                .uri("lb://customer-connect");
    }


    private static Buildable<Route> purchaseCoupon(PredicateSpec predicateSpec) {
        return predicateSpec.path("/purchase-coupon/**")
                .filters(f -> f.rewritePath("/purchase-coupon/(?<seg>.*)", "/api/customer/buyCoupon/${seg}"))
                .uri("lb://coupon-hub");
    }

    private static Buildable<Route> customerCoupons(PredicateSpec predicateSpec) {
        return predicateSpec.path("/customer-coupons")
                .filters(f -> f.rewritePath("/customer-coupons", "/api/customers/all"))
                .uri("lb://coupon-hub");
    }

    private static Buildable<Route> companyCoupons(PredicateSpec predicateSpec) {
        return predicateSpec.path("/company-coupons/**")
                .filters(f -> f.rewritePath("/company-coupons/(?<seg>.*)", "/api/company/all/${seg}"))
                .uri("lb://coupon-hub");
    }

    private static Buildable<Route> deleteCoupon(PredicateSpec predicateSpec) {
        return predicateSpec.path("/delete/**")
                .filters(f -> f.rewritePath("/delete/(?<seg>.*)", "/api/delete/${seg}"))
                .uri("lb://coupon-hub");
    }

    private static Buildable<Route> creatCoupon(PredicateSpec predicateSpec) {
        return predicateSpec.path("/add-coupon")
                .filters(f -> f.rewritePath("/add-coupon", "/api/creat"))
                .uri("lb://coupon-hub");
    }

    private static Buildable<Route> getCoupon(PredicateSpec predicateSpec) {
        return predicateSpec.path("/coupon/**")
                .filters(f -> f.rewritePath("/coupon/(?<seg>.*)", "/api/getOneCoupon/${seg}"))
                .uri("lb://coupon-hub");

    }

    private static Buildable<Route> parseToken(PredicateSpec predicateSpec) {
        return predicateSpec.path("/parse-token")
                .filters(f -> f.rewritePath("/parse-token", "/parse-token"))
                .uri("lb://auth-forge");
    }

    private static Buildable<Route> signIn(PredicateSpec predicateSpec) {
        return predicateSpec.path("/login")
                .filters(f -> f.rewritePath("/login", "/login"))
                .uri("lb://auth-forge");
    }

    private static Buildable<Route> signUp(PredicateSpec predicateSpec) {
        return predicateSpec.path("/signUp")
                .filters(f -> f.rewritePath("/signUp", "/sign-up"))
                .uri("lb://auth-forge");
    }

}
