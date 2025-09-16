package com.acciojob.Library_Management_System.Controllers;

import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Services.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/librarycard")
public class LibraryCardController {
    @Autowired
    private LibraryCardService libraryCardService;

    @PostMapping("/add")
    public String add(LibraryCard libraryCard){
        return libraryCardService.add(libraryCard);
    }

}
