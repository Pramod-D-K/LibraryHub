package com.acciojob.Library_Management_System.Controllers;

import com.acciojob.Library_Management_System.Entities.Student;
import com.acciojob.Library_Management_System.Services.LibraryCardService;
import com.acciojob.Library_Management_System.Services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private LibraryCardService libraryCardService;
    @PostMapping("/add")
    public String addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("/getTopStudents")
    public List<Student> getTopStudents(@RequestParam("branch") String branch,
                                        @RequestParam("cgpa")BigDecimal cgpa){
        try{
            return studentService.getTopStudents(branch, cgpa);
        } catch (Exception e) {
            return null;
        }

    }
    @GetMapping("/noOfBooksTaken")
    public String noOfBooksTaken(@RequestParam Integer studentId){

        try{
            return "Number of Books taken was  "+ libraryCardService.noOfBooksTaken(studentId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/printLogs")
    public void printLogs(){

        log.trace("Hi, this is trace trace");
        log.debug("Hi, this is trace debug");
        log.info("Hi, this is trace info");
        log.warn("Hi, this is trace warn");
        log.error("Hi, this is trace error");
    }

}
