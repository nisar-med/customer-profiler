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
import java.util.Calendar;
import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactions(Long customerId) {
        List<Transaction> transactions = new ArrayList<>();
        if(customerId != null) {
            transactions.addAll(transactionRepository.findByCustomerId(customerId));
        }
        else {
            transactionRepository.findAll().forEach(transactions::add);
        }
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
        CustomerProfile profile = new CustomerProfile(transactions);
        double balance = transactions.stream().mapToDouble(t->t.getAmount()).sum();
        profile.setBalance(balance);
        if(isAfternoonPerson(transactions)) {
            profile.addClassification("Afternoon Person");
        }
        if(isMorningPerson(transactions)) {
            profile.addClassification("Morning Person");
        }
        if(isBigSpender(transactions)) {
            profile.addClassification("Big Spender");
        }
        if(isBigTicketSpender(transactions)) {
            profile.addClassification("Bit Ticket Spender");
        }
        if(isPotentialSaver(transactions)) {
            profile.addClassification("Potential Saver");
        }

        return profile;
    }
    public boolean isAfternoonPerson(List<Transaction> transactions) {
        int afterMidDay=0, total=0;
        for(Transaction Transaction : transactions) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Transaction.getDate());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour > 12) {
                afterMidDay++;
            }
            total++;

        }
        double percentage = (double)afterMidDay / (double)total;
        return percentage > 0.5;
    }
    public boolean isMorningPerson(List<Transaction> transactions) {
        int afterMidDay=0, total=0;
        for(Transaction Transaction : transactions) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Transaction.getDate());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour < 12) {
                afterMidDay++;
            }
            total++;

        }
        double percentage = (double)afterMidDay / (double)total;
        return percentage > 0.5;
    }
    public boolean isBigSpender(List<Transaction> transactions) {
        double credits=0, debits=0;
        for(Transaction Transaction : transactions) {
            double amount = Transaction.getAmount();
            if(amount >= 0) {
                credits += amount;
            }
            else {
                debits += Math.abs(amount);
            }
        }
        double percentage = debits / credits;
        return percentage > 0.8;
    }
    public boolean isBigTicketSpender(List<Transaction> transactions) {
        for(Transaction Transaction : transactions) {
            double amount = Transaction.getAmount();
            if(amount < 0 && Math.abs(amount) > 1000) {
                return true;
            }
        }
        return false;
    }
    public boolean isPotentialSaver(List<Transaction> transactions) {
        double deposits = transactions.stream().
                filter(t->t.getAmount()>=0).
                mapToDouble(Transaction::getAmount).
                sum();
        double withdrawls = Math.abs(transactions.stream().
                filter(t->t.getAmount()<0).
                mapToDouble(Transaction::getAmount).
                sum());

        double percentage = withdrawls/deposits;
        return percentage < 0.25;
    }
}
