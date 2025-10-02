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
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(tx.getId())) {
                return i + 1; // position 1-based
            }
        }
        return -1; // non trouvé
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
        System.out.println("Niveau | Fee | Position | Est. Time (sec)");
        System.out.println("----------------------------------------");

        List<Transaction> baseList = new ArrayList<>(transactions); // copie du mempool

        for (FeePriority level : FeePriority.values()) {
            // Créer une copie temporaire
            Transaction tempTx = new Transaction(
                tx.getFromAddress(),
                tx.getToAddress(),
                tx.getAmount(),
                level,
                tx.getCryptoType()
            );
            tempTx.setFeePriority(level);

            // Liste temporaire pour test
            List<Transaction> testList = new ArrayList<>(baseList);
            testList.add(tempTx);

            // Trier par fee décroissante
            testList.sort((a, b) -> Double.compare(b.getFee(), a.getFee()));

            // Calcul de la position
            int position = 1;
            for (Transaction t : testList) {
                if (t.getId().equals(tempTx.getId())) break;
                position++;
            }

            // Estimation temps réaliste
            int estTime = 0;
            for (int i = 0; i < position - 1; i++) {
                FeePriority p = testList.get(i).getFeeLevel();
                switch (p) {
                    case RAPIDE: estTime += 1; break;
                    case STANDARD: estTime += 2; break;
                    case ECONOMIQUE: estTime += 5; break;
                }
            }

            System.out.printf("%-8s | %.6f | %-8d | %-15d\n", level.name(), tempTx.getFee(), position, estTime);
        }
    }

}
