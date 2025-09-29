package com.wallet.ui;


import com.wallet.utils.*;
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
	    
	    
	    public void start() {
	        while (true) {
	            System.out.println("\n=== CRYPTO WALLET SIMULATOR ===");
	            System.out.println("1. Créer un wallet");
	            System.out.println("2. Créer une transaction");
	            System.out.println("3. Voir un wallet par ID");
	            System.out.println("4. Voir une transaction par ID");
	            System.out.println("0. Quitter");
	            System.out.print("Votre choix : ");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // consommer le retour chariot

	            try {
	                switch (choice) {
	                    case 1:
	                        createWallet();
	                        break;
	                    case 2:
	                        createTransaction();
	                        break;
	                    case 3:
	                        viewWallet();
	                        break;
	                    case 4:
	                        viewTransaction();
	                        break;
	                    case 0:
	                        System.out.println("Bye 👋");
	                        return;
	                    default:
	                        System.out.println("Choix invalide.");
	                }
	            } catch (Exception e) {
	                System.err.println("Erreur : " + e.getMessage());
	            }
	        }
	    }

	    
	    
	    private void createWallet() throws SQLException {
	        System.out.print("Type de wallet (BITCOIN / ETHEREUM) : ");
	        String type = scanner.nextLine().toUpperCase();

	        Wallet wallet = walletService.createWallet(type);
	        System.out.println("Wallet créé avec succès :) !");
	        System.out.println("ID : " + wallet.getId());
	        System.out.println("Adresse : " + wallet.getAddress());
	        System.out.println("Type : " + wallet.getType());
	        System.out.println("Solde : " + wallet.getBalance() + " " + wallet.getType());
	    }
	    
	    
	    
	    
	    
	    private void createTransaction() throws SQLException {
	        System.out.print("Adresse source : ");
	        String from = scanner.nextLine();
	        System.out.print("Adresse destination : ");
	        String to = scanner.nextLine();
	        System.out.print("Montant : ");
	        double amount = scanner.nextDouble();
	        scanner.nextLine();

	        System.out.print("Fee level (ECONOMIQUE / STANDARD / RAPIDE) : ");
	        String feeStr = scanner.nextLine().toUpperCase();
	        FeePriority feeLevel = FeePriority.valueOf(feeStr);

	        System.out.print("Crypto (BITCOIN / ETHEREUM) : ");
	        String cryptoStr = scanner.nextLine().toUpperCase();
	        CryptoType cryptoType = CryptoType.valueOf(cryptoStr);

	        Transaction tx = transactionService.createTransaction(from, to, amount, feeLevel, cryptoType);

	        System.out.println(" Transaction créée !!");
	        System.out.println("ID : " + tx.getId());
	        System.out.println("De : " + tx.getFromAddress());
	        System.out.println("Vers : " + tx.getToAddress());
	        System.out.println("Montant : " + tx.getAmount());
	        System.out.println("Frais : " + tx.getFee() + " " + tx.getCryptoType());
	        System.out.println("Status : " + tx.getStatus());
	    }

	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
