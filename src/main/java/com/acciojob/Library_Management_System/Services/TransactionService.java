package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.Book;
import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Entities.Transaction;
import com.acciojob.Library_Management_System.Enums.CardStatus;
import com.acciojob.Library_Management_System.Enums.TransactionStatus;
import com.acciojob.Library_Management_System.Repositories.BookRepository;
import com.acciojob.Library_Management_System.Repositories.LibraryCardRepository;
import com.acciojob.Library_Management_System.Repositories.StudentRepository;
import com.acciojob.Library_Management_System.Repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryCardRepository libraryCardRepository;
    @Autowired
    private StudentRepository studentRepository;


    private final int MAX_NO_OF_ISSUED_BOOKS=3;
    private final int finePerDay=2;
    private final int maxDaysBook=7;

    public String issueBook(Integer cardId, Integer bookId) throws Exception {

        Transaction transaction= new Transaction();
        Optional<LibraryCard> optionalLibraryCard = libraryCardRepository.findById(cardId);
        LibraryCard libraryCard= optionalLibraryCard.orElseThrow(()-> new Exception("Card Id is Invalid"));
        log.info("student is  "+ libraryCard.getStudent().getName());
        Optional<Book>optionalBook = bookRepository.findById(bookId);
        log.info("Book is "+optionalBook.get().getTitle());
        Book book= optionalBook.orElseThrow(()-> new Exception("Book Id is Incorrect"));
        transaction.setBook(book);
        transaction.setLibraryCard(libraryCard);
        transaction.setIssuedDate(LocalDate.now());
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        log.info("transaction begins");

        LocalDate currentDate=LocalDate.now();
        if(currentDate.isAfter(libraryCard.getLastDate())){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction.setRootCauseOrOutput("The Card has been Expired");
            transaction.setIssuedDate(null);
            transaction.setFineAmount(null);
            log.error("Card was expired on "+ libraryCard.getLastDate());
            //transaction=transactionRepository.save(transaction);
            return "The Card has been Expired";
        }
        else if(book.getNoOfBooksLeft()==0||libraryCard.getNoOfBooksIssued()>=MAX_NO_OF_ISSUED_BOOKS){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction.setRootCauseOrOutput("Book is currently not present OR Books Limit Exceeded");
            transaction.setIssuedDate(null);
            transaction.setFineAmount(null);
            log.warn("Book not there in library OR student reached maximum trials");
            //transaction=transactionRepository.save(transaction);
            return "Book is currently not present OR Books Limit Exceeded";
        }
        else if(libraryCard.getCardStatus()==CardStatus.BLOCKED ||
                libraryCard.getCardStatus()==CardStatus.LOST ||
                libraryCard.getCardStatus()==CardStatus.NEW){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transaction.setRootCauseOrOutput("Card need to verify because "+libraryCard.getCardStatus());
            transaction.setIssuedDate(null);
            transaction.setFineAmount(null);
            log.warn("Card needs to check");
           // transaction=transactionRepository.save(transaction);
            return "Card need to verify";
        }

        if (transaction.getTransactionStatus()==TransactionStatus.FAILURE ){
            transaction.setRootCauseOrOutput("Transaction failure");
            transaction.setIssuedDate(null);
            transaction.setFineAmount(0);
            transaction=transactionRepository.save(transaction);
            log.error("Transaction failed");
            return "The transaction is FAILURE  with TransactionId  "+ transaction.getTransactionId();
        }
        libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()+1);
        book.setNoOfBooksLeft(book.getNoOfBooksLeft()-1);
        transaction.setTransactionStatus(TransactionStatus.ISSUED);

        bookRepository.save(book);
        libraryCardRepository.save(libraryCard);
        transaction.setRootCauseOrOutput("Book issued And need to collect back");
        transaction=transactionRepository.save(transaction);
        log.info("Book issued Successfully");
        return "Book has been Issued And with transactionId "+ transaction.getTransactionId();
    }


    public String returnBook(Integer cardId,Integer bookId) throws Exception{
        //By cardId bookId and status
        // mey chance get error for not correct cardId
        List<Transaction> transaction2= transactionRepository.findTransactionByLibraryCard_CardNoAndBook_BookIdAndTransactionStatusAndReturnDateIsNull(cardId,
                bookId,
                TransactionStatus.ISSUED);

        Optional<LibraryCard> optionalLibraryCard = libraryCardRepository.findById(cardId);
        LibraryCard libraryCard3= optionalLibraryCard.orElseThrow(()-> new Exception("Card Id is Invalid"));
        log.info("student name  "+ libraryCard3.getStudent().getName());
        Optional<Book>optionalBook = bookRepository.findById(bookId);
        Book book3= optionalBook.orElseThrow(()-> new Exception("Book Id is Incorrect"));
        log.info("Book with bookId is  "+optionalBook.get().getTitle());

        List<Transaction> transactionList= transactionRepository.findTransactionByLibraryCardAndBookAndReturnDateIsNull(
                libraryCard3,
                book3);

        List<Transaction> successTransactionList=new ArrayList<>();
         for (Transaction transaction: transactionList){
             if(transaction.getTransactionStatus()==TransactionStatus.ISSUED){
                 successTransactionList.add(transaction);
             }
         }
         if(successTransactionList.isEmpty()){
             throw  new Exception("No return Books in this cardId");
         }

         for (Transaction transaction:successTransactionList){
             if(Objects.equals(transaction.getBook().getBookId(), bookId)){
                 LocalDate returnDate= LocalDate.now();
                 long totalDays = ChronoUnit.DAYS.between(transaction.getIssuedDate(),returnDate);
                 int fine =0;
                 if(totalDays>maxDaysBook){
                     totalDays-=maxDaysBook;
                     fine = Math.toIntExact(totalDays*finePerDay);//safe convert into int
                 }
                 Optional<Book> book1= bookRepository.findById(bookId);
                 Book book= book1.orElseThrow(()-> new Exception("Book not found"));
                 Optional<LibraryCard> libraryCard1= libraryCardRepository.findById(cardId);
                 LibraryCard libraryCard= libraryCard1.orElseThrow(()-> new Exception("card not found"));
                 book.setNoOfBooksLeft(book.getNoOfBooksLeft()+1);
                 libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()-1);

                 transaction.setReturnDate(returnDate);
                 transaction.setFineAmount(fine);
                 transaction.setRootCauseOrOutput("Book returned");
                 transaction.setTransactionStatus(TransactionStatus.COMPLETED);
                 transactionRepository.save(transaction);
                 log.info("Book return is completed");
                 return "Book taken successfully And fine is "+ transaction.getFineAmount();
             }
         }
         return "this one is not borrowed book";
    }

    public List<Transaction> getTransactionList() throws Exception{

        List<Transaction> transactionList=transactionRepository.findAll();
        if(transactionList.isEmpty()){
            log.warn("Transaction is Empty");
        }
        return transactionList;
    }

    public String clearTransaction(){
        transactionRepository.deleteAll();
        return "Transaction List is CLEARED";
    }

}
