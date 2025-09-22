package com.acciojob.Library_Management_System.Entities;

import com.acciojob.Library_Management_System.Enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "fineGen")
//    @SequenceGenerator(name = "fineGen",sequenceName = "fineGenerator",allocationSize = 1)
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String transactionId;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreatedDate
    private LocalDate issuedDate;

    //below two are during return time
    private LocalDate returnDate;

    private Integer fineAmount;

    private  String rootCauseOrOutput;

    @JoinColumn
    @ManyToOne
    private Book book;

    @JoinColumn
    @ManyToOne
    private LibraryCard libraryCard;
}
