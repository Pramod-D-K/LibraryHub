package com.acciojob.Library_Management_System.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Student")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rollNo;

    @Column(name = "name", nullable = false,length = 300)
    private String name;

    private String branch;

    @Column(unique = false,insertable = true,updatable = true,scale = 1, precision = 10)
    private BigDecimal cgpa;

    @Column(unique = true)
    private String emailId;



}
