package com.acciojob.Library_Management_System.Controllers;

import com.acciojob.Library_Management_System.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
