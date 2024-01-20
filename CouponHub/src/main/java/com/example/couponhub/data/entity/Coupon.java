package com.example.couponhub.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;

import org.hibernate.type.descriptor.jdbc.CharJdbcType;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JdbcType(CharJdbcType.class)
    @Column(unique = true, updatable = false)
    private UUID uuid;
    @JdbcType(CharJdbcType.class)
    @Column(nullable = false)
    private UUID companyID;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String image;
    @ElementCollection
    @CollectionTable
    private List<UUID> customers;

    @PrePersist
    public void generateUuid() {
        uuid = UUID.randomUUID();
    }
}
