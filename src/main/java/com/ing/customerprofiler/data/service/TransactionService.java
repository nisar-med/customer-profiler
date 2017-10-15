package com.ing.customerprofiler.data.service;

import com.ing.customerprofiler.data.domain.CustomerProfile;
import com.ing.customerprofiler.data.entity.Transaction;
import com.ing.customerprofiler.data.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        transactionRepository.findAll().forEach(t->{
            transactions.add(t);
        });
        return transactions;
    }
    public CustomerProfile getCustomerProfile(Long customerId) {
        if(customerId == null) {
            return new CustomerProfile();
        }

        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        return new CustomerProfile(transactions);
    }
}
