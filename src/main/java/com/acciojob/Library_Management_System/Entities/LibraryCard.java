package com.acciojob.Library_Management_System.Entities;

import com.acciojob.Library_Management_System.BookStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "librarycard")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardNo;

    private BookStatus bookStatus;

    private int noOfBooksIssued;

    private Date lastDate;



}
