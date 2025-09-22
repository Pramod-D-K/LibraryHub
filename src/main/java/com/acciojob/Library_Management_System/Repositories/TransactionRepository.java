package com.acciojob.Library_Management_System.Repositories;

import com.acciojob.Library_Management_System.Entities.Book;
import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Entities.Transaction;
import com.acciojob.Library_Management_System.Enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import javax.smartcardio.Card;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction,String>{
    List<Transaction> findTransactionByLibraryCardAndBookAndReturnDateIsNull(
            LibraryCard card, Book book);

    Optional<Transaction> findTransactionByLibraryCard_CardNoAndBook_BookIdAndTransactionStatus(Integer cardId,
                                                                                                Integer bookId,
                                                                                                TransactionStatus transactionStatus);

}
