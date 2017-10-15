package com.ing.customerprofiler.data.repository;

import com.ing.customerprofiler.data.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    public List<Transaction> findByCustomerId(Long customerId);
    public List<Transaction> findByCustomerIdAndDateBetween(Long customerId, Date from, Date to);

}
