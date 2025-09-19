package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.Book;
import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Entities.Transaction;
import com.acciojob.Library_Management_System.Enums.CardStatus;
import com.acciojob.Library_Management_System.Enums.TransactionStatus;
import com.acciojob.Library_Management_System.Repositories.BookRepository;
import com.acciojob.Library_Management_System.Repositories.LibraryCardRepository;
import com.acciojob.Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    private int MAX_NO_OF_ISSUED_BOOKS=3;

    public String issueBook(Integer cardId, Integer bookId) throws Exception {

        Transaction transaction= new Transaction();
        Optional<LibraryCard> optionalLibraryCard = libraryCardRepository.findById(cardId);
        LibraryCard libraryCard= optionalLibraryCard.orElseThrow(()-> new Exception("Card Id is Invalid"));
        Optional<Book>optionalBook = bookRepository.findById(bookId);
        Book book= optionalBook.orElseThrow(()-> new Exception("Book Id is Incorrect"));
        transaction.setBook(book);
        transaction.setLibraryCard(libraryCard);
        transaction.setIssuedDate(LocalDate.now());
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        LocalDate currentDate=LocalDate.now();
        if(currentDate.isAfter(libraryCard.getLastDate())){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction=transactionRepository.save(transaction);
            return "The Card has been Expired";
        }
        else if(book.getNoOfBooksLeft()==0||libraryCard.getNoOfBooksIssued()>=MAX_NO_OF_ISSUED_BOOKS){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction=transactionRepository.save(transaction);
            return "Book is currently not present OR Books Limit Exceeded";
        }
        else if(libraryCard.getCardStatus()==CardStatus.BLOCKED ||
                libraryCard.getCardStatus()==CardStatus.LOST ||
                libraryCard.getCardStatus()==CardStatus.NEW){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction=transactionRepository.save(transaction);
            return "Card need to verify";
        }

        if (transaction.getTransactionStatus()==TransactionStatus.FAILURE ){
            transaction=transactionRepository.save(transaction);
            return "The transaction is FAILURE  with TransactionId  "+ transaction.getTransactionId();
        }
        libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()+1);
        book.setNoOfBooksLeft(book.getNoOfBooksLeft()-1);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        bookRepository.save(book);
        libraryCardRepository.save(libraryCard);
        transaction=transactionRepository.save(transaction);
        return "The transaction has been completed with TransactionId  "+ transaction.getTransactionId();
    }

}
