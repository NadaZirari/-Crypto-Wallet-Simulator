package com.wallet.repository;

import com.wallet.domain.Transaction;
import java.sql.SQLException;

public interface TransactionRepository {
	
	 void save(Transaction tx) throws SQLException;
	    Transaction findById(String id) throws SQLException;

}
