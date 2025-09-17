package com.acciojob.Library_Management_System.Entities;

import com.acciojob.Library_Management_System.Enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "card")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardNo;

    @Enumerated(value = EnumType.STRING)
    private CardStatus bookStatus;

    private int noOfBooksIssued;

    private LocalDate lastDate;

    @JoinColumn //  it tells that new column is added to the card table;
    @OneToOne // one to one connection
    private Student student;// which table should join



}
