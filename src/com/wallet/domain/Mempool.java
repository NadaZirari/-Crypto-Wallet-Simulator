package com.wallet.domain;

import com.wallet.domain.Transaction;
import com.wallet.utils.FeePriority;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mempool {

    private final List<Transaction> transactions;

    public Mempool() {
        this.transactions = new ArrayList<>();
    }

    // Ajouter une transaction et trier par fee
    public void addTransaction(Transaction tx) {
        if (!transactions.contains(tx)) {
            transactions.add(tx);
            sortByFee();
        }
    }

    // Tri décroissant par fee
    private void sortByFee() {
        Collections.sort(transactions, (a, b) -> Double.compare(b.getFee(), a.getFee()));
    }

    // Obtenir la position d'une transaction
    public int getPosition(Transaction tx) {
        sortByFee();
        int index = transactions.indexOf(tx);
        return index >= 0 ? index + 1 : -1;
    }

    public int getSize() {
        return transactions.size();
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // copie défensive
    }

    // Affichage du mempool avec troncature sécurisée
    public void displayMempool(Transaction userTx) {
        System.out.println("=== ÉTAT DU MEMPOOL ===");
        System.out.println("Transactions en attente : " + transactions.size());
        System.out.println("┌───────────────┬───────────────┬────────┐");
        System.out.printf("│ %-13s │ %-13s │ %-6s │\n", "FROM", "TO", "FEE");
        System.out.println("├───────────────┼───────────────┼────────┤");

        for (Transaction tx : transactions) {
            String label = tx.equals(userTx) ? ">>> " : "";
            String from = tx.getFromAddress() != null ? tx.getFromAddress() : "";
            if (from.length() > 10) {
                from = from.substring(0, 10) + "...";
            }

            String to = tx.getToAddress() != null ? tx.getToAddress() : "";
            if (to.length() > 10) {
                to = to.substring(0, 10) + "...";
            }

            System.out.printf("│ %s%-10s │ %-13s │ %.6f │\n", label, from, to, tx.getFee());
        }
        System.out.println("└───────────────┴───────────────┴────────┘");
    }

    // Comparer les 3 niveaux de fees pour une transaction
    public void compareFeeLevels(Transaction tx) {
        System.out.println("=== COMPARAISON DES FEES ===");
        System.out.println("Niveau | Fee | Position | Est. Time (min)");
        System.out.println("----------------------------------------");
        for (FeePriority level : FeePriority.values()) {
            tx.setFeePriority(level); // temporaire pour calcul
            double fee = tx.getFee();
            addTransaction(tx);
            int position = getPosition(tx);
            int estTime = position * 10;
            System.out.printf("%-8s | %.6f | %-8d | %-15d\n", level.name(), fee, position, estTime);
            transactions.remove(tx); // retirer pour ne pas perturber le mempool
        }
    }
}
