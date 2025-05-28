package com.bookhub.bookhub_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Book;
import java.time.LocalDate;


//아직 constraint 안함
@Entity
@Table(name = "discount_policies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    @Column(name = "policy_title", nullable = false)
    private String policyTitle;

    @Lob
    @Column(name = "policy_description", nullable = false)
    private String policyDescription;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_isbn")
    private Book bookIsbn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private BookCategory bookCategory;

    @Column(name = "total_price_achieve")
    private int totalPriceAchieve;

    @Column(name = "discount_percent", nullable = false)
    private int discountPercent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;



}
