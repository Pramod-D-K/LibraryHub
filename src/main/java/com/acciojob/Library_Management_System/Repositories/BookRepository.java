package com.acciojob.Library_Management_System.Repositories;

import com.acciojob.Library_Management_System.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

}
