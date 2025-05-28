package com.bookhub.bookhub_back.entity;

import jakarta.persistence.*;
import lombok.*;


//db에서 branch 옮기기 amount는 int로 바꾸기
@Entity
@Table(name = "customer_orders_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CustomerOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_orders_detail_id")
    private Long customerOrdersDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_order_id")
    private CustomerOrder customerOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn")
    private Book bookIsbn;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "price", nullable = false)
    private Long price;




}
