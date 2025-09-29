package com.wallet.domain;

import com.wallet.utils.CryptoType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Wallet {
	
	
	 private String id; // UUID
	    private CryptoType type;
	    private String address;
	    private double balance;
	   
	    private List<Transaction> transactions;

	    public Wallet(CryptoType type) {
	    	
	        this.id = UUID.randomUUID().toString();
	        this.type = type;
	        this.address = generateAddress(type);
	        this.balance = 0.0;
	        this.transactions = new ArrayList<>();
	    }

	    private String generateAddress(CryptoType type) {
	    	
	        if (type == CryptoType.BITCOIN) {
	        	
	            return "1" + UUID.randomUUID().toString().substring(0, 25);
	            
	        } else {
	        	
	            return "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 40);
	        }
	    }

	    public void addTransaction(Transaction tx) { transactions.add(tx); }

	    public String getId() { return id; }
	    
	    public CryptoType getType() { return type; }
	    
	    public String getAddress() { return address; }
	    
	    public double getBalance() { return balance; }
	    
	    public void setBalance(double balance) { this.balance = balance; }
	    
	    public List<Transaction> getTransactions() {
	    	return transactions; 
	    	}
	    
	    
	}
	


