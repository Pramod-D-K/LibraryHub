package com.acciojob.Library_Management_System.Controllers;

import com.acciojob.Library_Management_System.Entities.Book;
import com.acciojob.Library_Management_System.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody Book book,
                                  @RequestParam(value = "authorId", required = false)Integer authorId,
                                  @RequestParam(value = "authorName",required = false)String authorName){
        try{
            String result= bookService.addBook(book,authorId,authorName);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("getBooksOfAuthor")
    public ResponseEntity getBooksOfAuthor(@RequestParam(value = "authorId", required = false)Integer authorId,
                                           @RequestParam(value = "authorName",required = false) String authorName){
        try{
            List<String> ans = bookService.getBooksOfAuthor(authorId,authorName);
            return new ResponseEntity(ans,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("associateBookAndAuthor")
    public ResponseEntity associateBookAndAuthor(@RequestParam("bookId")Integer bookId,
                                                 @RequestParam("authorId")Integer authorId){
        try{
            String result = bookService.associateBookAndAuthor(bookId,authorId);
            return new ResponseEntity(result,HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
