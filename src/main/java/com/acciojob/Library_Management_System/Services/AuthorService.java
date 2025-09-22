package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.Author;
import com.acciojob.Library_Management_System.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public String addAuthor (Author author){
        author.setNoOfBooks(0);
        authorRepository.save(author);
        return "Author "+author.getAuthorName()+"  has been added to DB";
    }
    public Author highestNoOfBooks() throws Exception{
        List<Author> authorList= authorRepository.findAll();
        Author author=null;
        int books=0;

        for (Author author1: authorList){
            if(author1.getNoOfBooks()>books){
                author=author1;
                books=author1.getNoOfBooks();
            }
        }
        if(author==null){
            throw  new Exception("No author books found");
        }
        return author;
    }
}
