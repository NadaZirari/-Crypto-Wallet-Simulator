package com.wallet.repository;
import java.util.List;

import com.wallet.domain.Wallet;
import java.sql.SQLException;


import com.wallet.utils.CryptoType;
import com.wallet.domain.Wallet;
import com.wallet.repository.WalletRepository;

import java.sql.SQLException;
import java.util.UUID;

import com.wallet.domain.Wallet;
import java.sql.SQLException;

public interface WalletRepository {
	
	 void save(Wallet wallet) throws SQLException;
	    List<Wallet> findAll() throws SQLException;
	    Wallet findById(UUID id) throws SQLException;


}
