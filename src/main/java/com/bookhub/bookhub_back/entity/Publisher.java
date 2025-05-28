package com.bookhub.bookhub_back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.LifecycleState;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

//180
//완료
@Entity
@Table(name = "publishers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long publisherId;

    @Column (name = "publisher_name", nullable = false)
    private String publisherName;

    @OneToMany(mappedBy = "publisherId")
    private List<Book> books = new ArrayList<>();

}
