package com.wallet.repository;

import com.wallet.domain.Transaction;
import java.sql.SQLException;
import java.util.List;

public interface TransactionRepository {
	
	 void save(Transaction tx) throws SQLException;
	    Transaction findById(String id) throws SQLException;
	    List<Transaction> findAll() throws SQLException;

}
