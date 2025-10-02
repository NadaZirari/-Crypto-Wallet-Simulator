package com.wallet.ui;

import java.util.List;

import com.wallet.utils.*;
import com.wallet.domain.*;
import com.wallet.repository.*;
import com.wallet.service.*;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;


public class MainMenu {
	
	 private final WalletService walletService;
	    private final TransactionService transactionService;
	    private final Scanner scanner;

	    private Wallet selectedWallet;
	    
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
	            System.out.println("2. Sélectionner un wallet existant"); // AJOUT
	            System.out.println("3. Créer une transaction");

	          /*  System.out.println(". Voir un wallet par ID");
	            System.out.println("4. Voir une transaction par ID");*/
	            System.out.println("4. Voir ma position dans le mempool");
	            System.out.println("5. Comparer les 3 niveaux de frais");
	            System.out.println("6. Consulter l'état du mempool");

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
	                    	selectWallet();
	                        break;
	                    case 3:
	                        createTransaction();

	                       // viewWallet();
	                        break;
	                    //case 4:
	                       // viewTransaction();
	                       // break;
	                    case 4:
	                        viewMempoolPosition();
	                        break;
	                    case 5:
	                        compareFeeLevels();
	                        break;
	                    case 6:
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
	    
	    
	 // Afficher tous les wallets
	    private void listWallets() throws SQLException {
	        System.out.println("\n=== LISTE DES WALLETS ===");
	        List<Wallet> wallets = walletService.getAllWallets();

	        if (wallets.isEmpty()) {
	            System.out.println("Aucun wallet trouvé.");
	        } else {
	            int i = 1;
	            for (Wallet w : wallets) {
	                System.out.printf("%d. ID: %s | Adresse: %s | Type: %s | Solde: %.6f\n",
	                        i++, w.getId(), w.getAddress(), w.getType(), w.getBalance());
	            }
	        }
	    }

	    // Sélectionner un wallet existant
	    private void selectWallet() throws SQLException {
	    	List<Wallet> wallets = walletService.getAllWallets();

	        if (wallets.isEmpty()) {
	            System.out.println("Aucun wallet disponible, créez-en un d'abord.");
	            return;
	        }

	        listWallets();
	        System.out.print("Choisissez un wallet (numéro) : ");
	        int choice = scanner.nextInt();
	        scanner.nextLine();

	        if (choice < 1 || choice > wallets.size()) {
	            System.out.println("Choix invalide.");
	        } else {
	            selectedWallet = wallets.get(choice - 1);
	            System.out.println("Wallet sélectionné : " + selectedWallet.getId() + " (" + selectedWallet.getType() + ")");
	        }
	    }

	    // Exemple : utiliser selectedWallet pour créer une transaction
	    private void createTransaction() throws SQLException {
	        if (selectedWallet == null) {
	            System.out.println("⚠ Veuillez d'abord sélectionner un wallet !");
	            return;
	        }

	        System.out.print("Adresse destination : ");
	        String to = scanner.nextLine();
	        System.out.print("Montant : ");
	        double amount = scanner.nextDouble();
	        scanner.nextLine();

	        System.out.print("Fee level (ECONOMIQUE / STANDARD / RAPIDE) : ");
	        String feeStr = scanner.nextLine().toUpperCase();
	        FeePriority feeLevel = FeePriority.valueOf(feeStr);

	        Transaction tx = transactionService.createTransaction(
	                selectedWallet.getAddress(), // source = wallet sélectionné
	                to,
	                amount,
	                feeLevel,
	                selectedWallet.getType()
	        );
	        mempool.addTransaction(tx);

	        System.out.println(" Transaction créée !!");
	        System.out.println("ID : " + tx.getId());
	        System.out.println("De : " + tx.getFromAddress());
	        System.out.println("Vers : " + tx.getToAddress());
	        System.out.println("Montant : " + tx.getAmount());
	        System.out.println("Frais : " + tx.getFee() + " " + tx.getCryptoType());
	        System.out.println("Status : " + tx.getStatus());
	    }
	    
	    
	   /*private void createTransaction() throws SQLException {
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
	    }*/

	    
	    
	  
	        
	        private void viewWallet() throws SQLException {
	            System.out.print("Entrez l'ID du wallet : ");
	            String idInput = scanner.nextLine();

	            UUID walletId;
	            try {
	                walletId = UUID.fromString(idInput); // conversion sécurisée
	            } catch (IllegalArgumentException e) {
	                System.out.println("ID invalide, veuillez entrer un UUID valide !");
	                return;
	            }
	        

	            Wallet wallet = walletService.getWalletById(walletId);
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

	   /* private void viewMempoolPosition() throws SQLException {
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
	    }*/
	    private void viewMempoolPosition() throws SQLException {
	        System.out.print("Entrez l'ID de la transaction : ");
	        String idStr = scanner.nextLine();
	        UUID txId = UUID.fromString(idStr);

	        Transaction tx = null;
	        // Chercher la transaction dans le mempool
	        for (Transaction t : mempool.getTransactions()) {
	            if (t.getId().equals(txId)) {
	                tx = t;
	                break;
	            }
	        }

	        if (tx != null) {
	            int pos = mempool.getPosition(tx);
	            System.out.println("Votre transaction est en position " + pos + " sur " + mempool.getSize());
	            System.out.println("Temps estimé : " + (pos * 10) + " secondes");
	        } else {
	            System.out.println("Transaction introuvable dans le mempool.");
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
