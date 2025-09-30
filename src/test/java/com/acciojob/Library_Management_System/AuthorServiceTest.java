package com.acciojob.Library_Management_System;

import com.acciojob.Library_Management_System.Entities.Author;
import com.acciojob.Library_Management_System.Repositories.AuthorRepository;
import com.acciojob.Library_Management_System.Services.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void authorWithHighestNoOfBooks() throws Exception {
        //creating dummy data
        Author A1= new Author(1,"ABC",4,55,"abc");
        Author A2= new Author(2,"DEF",5,65,"def");
        Author A3= new Author(3,"GHI",6,75,"ghi");
        List<Author> authorList=new ArrayList<>();
        authorList.add(A1);
        authorList.add(A2);
        authorList.add(A3);

        //call the method
        Mockito.when(authorRepository.findAll()).thenReturn(authorList);
        Author author= authorService.highestNoOfBooks();

        //compare the both the data
        Assertions.assertEquals(A3,author);
    }
}
