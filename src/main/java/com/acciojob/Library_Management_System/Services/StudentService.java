package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.LibraryCard;
import com.acciojob.Library_Management_System.Entities.Student;
import com.acciojob.Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public String addStudent(Student student){
        studentRepository.save(student);
        return "Student "+student.getName()+" has been Saves to Db ";
    }

    public List<Student> getTopStudents(String branch, BigDecimal cgpa)throws Exception{

        List<Student> studentList =  studentRepository.findAllByBranchAndCgpaGreaterThan(branch, cgpa);
        if(studentList.isEmpty()){
            throw new Exception("No student found");
        }
        return  studentList;
    }

}
