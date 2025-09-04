package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.Student;
import com.acciojob.Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(Student student){
        studentRepository.save(student);
        return "Student "+ student.getStudentName() +" has been Saves to Db ";
    }
}
