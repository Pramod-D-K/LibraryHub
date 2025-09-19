package com.acciojob.Library_Management_System.Repositories;

import com.acciojob.Library_Management_System.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction,String>{

}
