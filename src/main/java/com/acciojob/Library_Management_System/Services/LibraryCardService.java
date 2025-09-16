package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Repositories.LibraryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryCardService {
    @Autowired
    private LibraryCardRepository libraryCardRepository;

    public String add(LibraryCard libraryCard){
        libraryCardRepository.save(libraryCard);
        return "Card info of student added to Db";
    }

}
