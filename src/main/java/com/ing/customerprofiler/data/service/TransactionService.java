package com.ing.customerprofiler.data.service;

import com.ing.customerprofiler.data.domain.CustomerProfile;
import com.ing.customerprofiler.data.entity.Transaction;
import com.ing.customerprofiler.data.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
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
    public CustomerProfile getCustomerProfile(Long customerId, String period) {
        if(customerId == null) {
            return new CustomerProfile();
        }

        List<Transaction> transactions;
        if(period != null) {
            try {
                LocalDate from = YearMonth.parse(period).atDay(1);
                LocalDate to = YearMonth.parse(period).atEndOfMonth();
                transactions = transactionRepository.
                        findByCustomerIdAndDateBetween(customerId, Date.valueOf(from), Date.valueOf(to));
            }catch (DateTimeParseException ex) {
                transactions = new ArrayList<>();
            }
        }
        else {
            transactions = transactionRepository.findByCustomerId(customerId);
        }
        return new CustomerProfile(transactions);
    }
}
