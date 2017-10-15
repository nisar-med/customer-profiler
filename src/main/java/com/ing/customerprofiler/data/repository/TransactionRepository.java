package com.ing.customerprofiler.data.repository;

import com.ing.customerprofiler.data.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
