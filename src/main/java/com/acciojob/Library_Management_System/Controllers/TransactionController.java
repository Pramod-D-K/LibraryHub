package com.acciojob.Library_Management_System.Controllers;

import com.acciojob.Library_Management_System.Entities.Transaction;
import com.acciojob.Library_Management_System.Services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PutMapping("/issueBook")
    public String issueBook(@RequestParam("cardId")Integer cardId,
                            @RequestParam("bookId") Integer bookId){

        try{
            String result = transactionService.issueBook(cardId, bookId);
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam("cardId")Integer cardId,
                                            @RequestParam("bookId") Integer bookId){
        try{
            String result = transactionService.returnBook(cardId, bookId);
            return ResponseEntity.accepted().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getTransaction")
    public List<Transaction> getTransactionList(){
        try{
            return transactionService.getTransactionList();
        } catch (Exception e) {
            log.warn("TransactionList is EMPTY");
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/clearTransaction")
    public String clearTransaction(){
        return transactionService.clearTransaction();
    }
}
