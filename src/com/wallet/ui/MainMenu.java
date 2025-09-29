package com.wallet.ui;



import com.wallet.domain.*;
import com.wallet.repository.*;
import com.wallet.service.*;

import java.sql.SQLException;
import java.util.Scanner;


public class MainMenu {
	
	 private final WalletService walletService;
	    private final TransactionService transactionService;
	    private final Scanner scanner;

	    
	    public MainMenu() {
	        // Initialisation des repositories et services
	        WalletRepository walletRepository = new WalletRepositoryImpl();
	        TransactionRepository transactionRepository = new TransactionRepositoryImpl();

	        this.walletService = new WalletServiceImpl(walletRepository);
	        this.transactionService = new TransactionServiceImpl(transactionRepository);

	        this.scanner = new Scanner(System.in);
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
