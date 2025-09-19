package com.acciojob.Library_Management_System.Entities;

import com.acciojob.Library_Management_System.Enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Book")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer bookId;

    @Column(unique = true)
    private String title;

    private Integer noOfPages;

    private int price;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    private Integer booksQuantity;

    private  int noOfBooksLeft;

    @Column(scale = 1)
    private BigDecimal rating;

    @JoinColumn
    @ManyToOne
    private Author author;

}
