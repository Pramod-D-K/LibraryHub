package com.acciojob.Library_Management_System.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer authorId;

    @Column(name="name")
    private String authorName;

    private Integer age;

    @Column(unique = true)
    private String emailId;

    @Column(scale = 1)
    private BigDecimal rating;

}
