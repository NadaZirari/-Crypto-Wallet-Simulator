package com.wallet.domain;

public class Wallet {
	
	
	 private String id; // UUID
	    private CryptoType type;
	    private String address;
	    private double balance;
	    private List<Transaction> transactions = new ArrayList<>();
	
	

}
