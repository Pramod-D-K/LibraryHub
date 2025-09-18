package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.Author;
import com.acciojob.Library_Management_System.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public String addAuthor (Author author){
        author.setNoOfBooks(0);
        authorRepository.save(author);
        return "Author has been added to DB";
    }
}
