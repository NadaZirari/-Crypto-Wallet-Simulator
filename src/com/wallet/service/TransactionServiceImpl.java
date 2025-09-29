package com.wallet.service;
import com.wallet.utils.*;

import com.wallet.domain.*;
import com.wallet.repository.TransactionRepository;

import java.sql.SQLException;

public class TransactionServiceImpl implements TransactionService{


	 private final TransactionRepository transactionRepository;

	    public TransactionServiceImpl(TransactionRepository transactionRepository) {
	        this.transactionRepository = transactionRepository;
	    }

	    @Override
	    public Transaction createTransaction(String from, String to, double amount, FeePriority feeLevel, CryptoType type) throws SQLException {
	        if (amount <= 0) {
	            throw new IllegalArgumentException("Le montant doit être positif");
	        }

	        Transaction tx = new Transaction(from, to, amount, feeLevel, type);
	        transactionRepository.save(tx);
	        return tx;
	    }

	    @Override
	    public Transaction getTransactionById(String id) throws SQLException {
	        return transactionRepository.findById(id);
	    }
	}
