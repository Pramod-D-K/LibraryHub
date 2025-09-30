package com.acciojob.Library_Management_System;


import com.acciojob.Library_Management_System.Entities.Author;
import com.acciojob.Library_Management_System.Entities.Book;
import com.acciojob.Library_Management_System.Enums.Genre;
import com.acciojob.Library_Management_System.Repositories.BookRepository;
import com.acciojob.Library_Management_System.Services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;
    @Test
    public void getAuthorBooks() throws Exception {
        //dummy data
        Author A1= new Author(1,"ABC",4,55,"abc");
        Author A2= new Author(2,"DEF",5,65,"def");
        Author A3= new Author(3,"GHI",6,75,"ghi");
        List<Author> authorList=new ArrayList<>();
        authorList.add(A1);
        authorList.add(A2);
        authorList.add(A3);


        Book B1=new Book(1,"AAA",89,890, Genre.HORROR,3,3, BigDecimal.valueOf(9.3),A1);
        Book B2=new Book(2,"BBB",89,890,Genre.HORROR,3,3,new BigDecimal(7.4),A1);
        Book B3=new Book(3,"CCC",89,890,Genre.HORROR,3,3,BigDecimal.valueOf(5.5),A2);
        List<Book> bookList= new ArrayList<>();
        bookList.add(B1);
        bookList.add(B2);
        bookList.add(B3);

        List<String> stringList=new ArrayList<>();
        stringList.add(B1.getTitle());
        stringList.add(B2.getTitle());

        //call the method
        Mockito.when(bookRepository.findAll()).thenReturn(bookList);
        List<String> ans= bookService.getBooksOfAuthor(1,null);

        //compare data with actual value with demo
        Assertions.assertEquals(stringList,ans);

    }
}
