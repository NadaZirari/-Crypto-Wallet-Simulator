package com.wallet.service;

import com.wallet.domain.Wallet;
import java.sql.SQLException;

public interface WalletService {
	 Wallet createWallet(String type) throws SQLException;
	    Wallet getWalletById(String id) throws SQLException;

}
