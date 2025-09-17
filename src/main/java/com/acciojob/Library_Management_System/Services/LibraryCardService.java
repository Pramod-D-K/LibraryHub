package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Enums.CardStatus;
import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Entities.Student;
import com.acciojob.Library_Management_System.Repositories.LibraryCardRepository;
import com.acciojob.Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LibraryCardService {
    @Autowired
    private LibraryCardRepository libraryCardRepository;
    @Autowired
    private StudentRepository studentRepository;
    public String add(){
        LibraryCard card=new LibraryCard();
        card.setBookStatus(CardStatus.NEW);
        card.setNoOfBooksIssued(0);
       // Date experyDate= new Date(2025,1,1);
        LocalDate experyDate=LocalDate.of(2025,01,01);
        card.setLastDate(experyDate);
        card=libraryCardRepository.save(card);
        return "Card has been generated with cardId "+ card.getCardNo();
    }

    public String associateStudentAndCArd(Integer rollNo,Integer cardNo){
        LibraryCard card = libraryCardRepository.findById(cardNo).get();
        Student student= studentRepository.findById(rollNo).get();
        card.setBookStatus(CardStatus.ISSUED);
        card.setStudent(student);
        libraryCardRepository.save(card);
        return  "Student "+ card.getStudent().getName() + "And Card "+card.getCardNo()+" Associated ";
    }

}
