package com.wallet.domain;

import java.util.ArrayList;
import java.util.List;

public class Mempool {
	
	
	
	private List<Transaction> transactions;

    public Mempool() { this.transactions = new ArrayList<>(); }

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
        sortByFee();
    }

    public void sortByFee() {
        transactions.sort((a, b) -> Double.compare(b.getFee(), a.getFee()));
    }

    public int getPosition(Transaction tx) {
        sortByFee();
        return transactions.indexOf(tx) + 1;
    }

    public int getSize() { return transactions.size(); }
    
    public void displayMempool(Transaction userTx) {
        System.out.println("=== ÉTAT DU MEMPOOL ===");
        System.out.println("Transactions en attente : " + transactions.size());
        System.out.println("┌─────────────────────────────┬────────┐");
        for (Transaction tx : transactions) {
            String label = tx == userTx ? ">>> VOTRE TX: " : "";
            System.out.printf("│ %s%s │ %.6f │\n",
                    label,
                    tx.getToAddress().substring(0, Math.min(8, tx.getToAddress().length())) + "...",
                    tx.getFee());
        }
        System.out.println("└─────────────────────────────┴────────┘");
    }

}
