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
	            System.out.println("5. Voir ma position dans le mempool");
	            System.out.println("6. Comparer les 3 niveaux de frais");
	            System.out.println("7. Consulter l'état du mempool");

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
	                    case 5:
	                        viewMempoolPosition();
	                        break;
	                    case 6:
	                        compareFeeLevels();
	                        break;
	                    case 7:
	                        displayMempool();
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

	    
	    
	    private void viewWallet() throws SQLException {
	        System.out.print("Entrez l'ID du wallet : ");
	        String id = scanner.nextLine();

	        Wallet wallet = walletService.getWalletById(id);
	        if (wallet != null) {
	            System.out.println("=== WALLET ===");
	            System.out.println("ID : " + wallet.getId());
	            System.out.println("Adresse : " + wallet.getAddress());
	            System.out.println("Type : " + wallet.getType());
	            System.out.println("Solde : " + wallet.getBalance());
	        } else {
	            System.out.println(" Aucun wallet trouvé avec cet ID.");
	        }
	    }

	    
	    private void viewTransaction() throws SQLException {
	        System.out.print("Entrez l'ID de la transaction : ");
	        String id = scanner.nextLine();

	        Transaction tx = transactionService.getTransactionById(id);
	        if (tx != null) {
	            System.out.println("=== TRANSACTION ===");
	            System.out.println("ID : " + tx.getId());
	            System.out.println("De : " + tx.getFromAddress());
	            System.out.println("Vers : " + tx.getToAddress());
	            System.out.println("Montant : " + tx.getAmount());
	            System.out.println("Frais : " + tx.getFee());
	            System.out.println("Status : " + tx.getStatus());
	            System.out.println("Type : " + tx.getCryptoType());
	        } else {
	            System.out.println("Aucune transaction trouvée avec cet ID.");
	        }
	    }
	    
	    private final Mempool mempool = new Mempool();

	    private void viewMempoolPosition() throws SQLException {
	        System.out.print("Entrez l'ID de la transaction : ");
	        String id = scanner.nextLine();
	        Transaction tx = transactionService.getTransactionById(id);
	        if (tx != null) {
	            int pos = mempool.getPosition(tx);
	            System.out.println("Votre transaction est en position " + pos + " sur " + mempool.getSize());
	            System.out.println("Temps estimé : " + (pos * 10) + " secondes");
	        } else {
	            System.out.println("Transaction introuvable.");
	        }
	    }

	    private void compareFeeLevels() {
	        System.out.print("Adresse source : ");
	        String from = scanner.nextLine();
	        System.out.print("Adresse destination : ");
	        String to = scanner.nextLine();
	        System.out.print("Montant : ");
	        double amount = scanner.nextDouble();
	        scanner.nextLine();

	        for (FeePriority level : FeePriority.values()) {
	            Transaction tx = new Transaction(from, to, amount, level, CryptoType.BITCOIN);
	            mempool.addTransaction(tx);
	            System.out.println(level + " -> Fee: " + tx.getFee() + " Position: " + mempool.getPosition(tx));
	        }
	    }

	    private void displayMempool() {
	        mempool.displayMempool(null);
	    }
	    
	public static void main(String[] args) {

		
		 new MainMenu().start();
	}

}
