package com.wallet.service;



import com.wallet.utils.CryptoType;
import com.wallet.domain.Wallet;
import com.wallet.repository.WalletRepository;

import java.sql.SQLException;
import java.util.UUID;

public class WalletServiceImpl implements WalletService {

	
	 private final WalletRepository walletRepository;

	    // Injection via constructeur (bon pour les tests et l’extensibilité)
	    public WalletServiceImpl(WalletRepository walletRepository) {
	        this.walletRepository = walletRepository;
	    }

	    @Override
	    public Wallet createWallet(String type) throws SQLException {
	        CryptoType cryptoType = CryptoType.valueOf(type.toUpperCase());

	        // Génération d’une adresse unique
	        String address = cryptoType == CryptoType.BITCOIN
	                ? "1" + UUID.randomUUID().toString().replace("-", "").substring(0, 25)
	                : "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 32);

	        Wallet wallet = new Wallet(UUID.randomUUID().toString(), address, 0.0, cryptoType);

	        walletRepository.save(wallet);
	        return wallet;
	    }

	    @Override
	    public Wallet getWalletById(String id) throws SQLException {
	        return walletRepository.findById(id);
	    }
	
}
