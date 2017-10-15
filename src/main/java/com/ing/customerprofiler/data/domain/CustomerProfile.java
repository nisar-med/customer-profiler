package com.ing.customerprofiler.data.domain;

import com.ing.customerprofiler.data.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CustomerProfile {
    private List<String> classifications;
    private List<Transaction> transactions;
    private double balance;

    public CustomerProfile() {
        this.classifications = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public CustomerProfile(List<Transaction> transactions) {
        this.classifications = new ArrayList<>();
        this.transactions = transactions;
    }

    public void addClassification(String classification) {
        this.classifications.add(classification);
    }
    public List<String> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<String> classifications) {
        this.classifications = classifications;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> Transactions) {
        this.transactions = transactions;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
