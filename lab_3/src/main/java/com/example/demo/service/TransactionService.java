package com.example.demo.service;


import com.example.demo.dto.CreateTransactionDTO;
import com.example.demo.dto.UpdateTransactionDTO;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repo.TransactionRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepo transactionRepository;
    private final UserRepo userRepo;

    @Autowired
    public TransactionService(TransactionRepo transactionRepository, UserRepo userRepo) {
        this.transactionRepository = transactionRepository;
        this.userRepo = userRepo;
    }

    public Transaction createTransaction(CreateTransactionDTO dto) {
        User sender = userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepo.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setAmount(dto.getAmount());
        transaction.setStatus(dto.getStatus());
        transaction.setDate(dto.getDate());

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(int id, UpdateTransactionDTO dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        User sender = userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepo.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        transaction.setSender(sender);
        transaction.setAmount(dto.getAmount());
        transaction.setStatus(dto.getStatus());
        transaction.setDate(dto.getDate());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}


