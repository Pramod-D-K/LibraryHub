package com.acciojob.Library_Management_System.Repositories;

import com.acciojob.Library_Management_System.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    Student findStudentByName(String name);
    List<Student> findAllByBranchAndCgpaGreaterThan(String branch, BigDecimal cgpa);

}
