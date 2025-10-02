package com.wallet.service;

import java.util.List;
import java.util.UUID;
import java.sql.SQLException;
import java.util.List;

import com.wallet.domain.Wallet;

public interface WalletService {
	 Wallet createWallet(String type) throws SQLException;
	    Wallet getWalletById(UUID id) throws SQLException;
	    List<Wallet> getAllWallets() throws SQLException;

}
