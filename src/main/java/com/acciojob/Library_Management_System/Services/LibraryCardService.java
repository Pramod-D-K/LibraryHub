package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Enums.CardStatus;
import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Entities.Student;
import com.acciojob.Library_Management_System.Repositories.LibraryCardRepository;
import com.acciojob.Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LibraryCardService {
    @Autowired
    private LibraryCardRepository libraryCardRepository;
    @Autowired
    private StudentRepository studentRepository;
    public String add(Integer studentId) throws Exception{
        LibraryCard card=new LibraryCard();
        card.setCardStatus(CardStatus.NEW);
        card.setNoOfBooksIssued(0);
       // Date expiryDate= new Date(2025,1,1);
        LocalDate expiryDate =LocalDate.of(2025,01,01);
        if(studentId!=null){
            Optional<Student>optionalStudent= studentRepository.findById(studentId);
            Student student= optionalStudent.orElseThrow(()-> new Exception("Student Id not present"));
            card.setStudent(student);
            card.setCardStatus(CardStatus.ISSUED);
        }
        card.setLastDate(expiryDate);
        card=libraryCardRepository.save(card);
        return "Card has been generated with cardId "+ card.getCardNo();
    }

    public String associateStudentAndCArd(Integer rollNo,Integer cardNo) throws Exception{
        Optional<LibraryCard>optionalLibraryCard=libraryCardRepository.findById(cardNo);
        LibraryCard card = optionalLibraryCard.orElseThrow(()->new Exception ("Card Id Not present"));
        Optional<Student>optionalStudent=studentRepository.findById(cardNo);
        Student student = optionalStudent.orElseThrow(()->new Exception ("Student Id Not present"));
        if(card.getCardStatus()!=null&&card.getCardStatus().equals(CardStatus.ISSUED)){
            throw new Exception("Card Already Used");
        }
        card.setCardStatus(CardStatus.ISSUED);
        card.setStudent(student);
        libraryCardRepository.save(card);
        return  "Student "+ card.getStudent().getName() + "And Card "+card.getCardNo()+" Associated ";
    }

}
