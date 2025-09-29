package com.wallet.service;

import com.wallet.domain.Transaction;
import com.wallet.utils.FeePriority;
import com.wallet.utils.CryptoType;
import java.sql.SQLException;

public interface TransactionService {
	 Transaction createTransaction(String from, String to, double amount, FeePriority feeLevel, CryptoType type) throws SQLException;
	    Transaction getTransactionById(String id) throws SQLException;

}
