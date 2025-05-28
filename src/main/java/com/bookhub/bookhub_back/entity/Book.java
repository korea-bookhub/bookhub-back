package com.bookhub.bookhub_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_isbn")
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private BookCategory categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisherId;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(name = "book_price", nullable = false)
    private Integer bookPrice;

    @Column(name = "published_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publishedDate;

    @Column(name = "cover_url", nullable = false)
    private String coverUrl;

    @Column(name = "page_count", nullable = false)
    private String pageCount;

    @Column(name = "language", nullable = false)
    private String language;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private DiscountPolicy policyId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
