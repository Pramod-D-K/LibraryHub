package com.acciojob.Library_Management_System.Repositories;

import com.acciojob.Library_Management_System.Entities.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Integer> {
    Optional<LibraryCard> findLibraryCardByStudent_RollNo(Integer studentId);
}
