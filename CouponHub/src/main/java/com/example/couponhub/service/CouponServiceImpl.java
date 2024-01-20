package com.example.couponhub.service;

import com.example.couponhub.data.dto.CouponDto;
import com.example.couponhub.data.entity.Coupon;
import com.example.couponhub.data.repository.CouponRepository;
import com.example.couponhub.mapper.AppMapper;
import com.example.couponhub.service.ex.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final AppMapper mapper;

    /**
     * Description: This function adds a new coupon to a company's list of coupons and saves it to the database if certain conditions are met.
     *
     * @param couponDto
     * @return A CouponDto object representing the added coupon.
     */
    @Override
    public CouponDto addCoupon(CouponDto couponDto) {
        if (couponDto != null) {
            if (couponRepository.findByUuid(couponDto.getUuid()).isEmpty()) {
                return mapper.map(couponRepository.save(mapper.map(couponDto)));
            } else throw new AlreadyException("coupon is already exists");
        } else throw new EmptyException("Cannot add empty coupon");
    }

    /**
     * Description: This function deletes a coupon with the given UUID from the company's list of coupons and the database.
     *
     * @param couponUuid
     */
    @Override
    public void deleteCoupon(UUID couponUuid) {
        Optional<Coupon> couponOptional = couponRepository.findByUuid(couponUuid);
        couponRepository.delete(couponOptional.get());
    }

    /**
     * Description: This function retrieves all coupons associated with a particular company based on its UUID.
     *
     * @param companyUuid
     * @return A list of CouponDto objects representing all the coupons associated with the specified company.
     */
    @Override
    public List<CouponDto> getAllCoupons(UUID companyUuid) {
        return couponRepository.findCouponsByCompanyID(companyUuid)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

    }

    @Override
    public List<CouponDto> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all coupons associated with a specific customer.
     *
     * @param customerUuid The UUID of the customer for which to retrieve the coupons.
     * @return A list of CouponDto objects representing all the coupons associated with the specified customer.
     */
    @Override
    public List<CouponDto> getAllCouponsOfCustomer(UUID customerUuid) {
        return couponRepository.findAll()
                .stream()
                .filter(coupon -> coupon.getCustomers().stream().anyMatch(uuid -> uuid.equals(customerUuid)))
                .map(mapper::map)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a specific coupon by its UUID.
     *
     * @param couponUuid The UUID of the coupon to retrieve.
     * @return A CouponDto object representing the retrieved coupon.
     * @throws NotExistException if the coupon does not exist.
     */
    @Override
    public CouponDto getOneCoupon(UUID couponUuid) {
        Optional<Coupon> couponOptional = couponRepository.findByUuid(couponUuid);
        boolean present = couponOptional.isPresent();
        if (present) {
            return mapper.map(couponOptional.get());
        } else throw new NotExistException("the coupon not exist");
    }

    /**
     * Description: This function adds a coupon to a customer's list of purchased coupons, updates the coupon's availability, and saves the customer and coupon to the database.
     *
     * @param customerUuid
     * @param couponUuid
     * @return A CouponDto object representing the purchased coupon.
     */
    @Override
    public CouponDto addCouponPurchase(UUID customerUuid, UUID couponUuid) {

        Coupon coupon = couponRepository.findByUuid(couponUuid).get();
        if (coupon.getAmount() == 0) {
            throw new EmptyException("This coupon is empty");
        }
        if (coupon.getEndDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new InvalidException("Sorry, the coupon has expired.");
        }
        coupon.getCustomers().add(customerUuid);
        coupon.setAmount(coupon.getAmount() - 1);
        return mapper.map(couponRepository.save(coupon));
    }

}
