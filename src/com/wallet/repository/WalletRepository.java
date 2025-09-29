package com.wallet.repository;

import com.wallet.domain.Wallet;
import java.sql.SQLException;

public interface WalletRepository {
	
	 void save(Wallet wallet) throws SQLException;
	    Wallet findById(String id) throws SQLException;

}
