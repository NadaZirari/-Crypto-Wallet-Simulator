package com.wallet.domain;

import com.wallet.utils.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
	
	private String id;
    private String fromAddress;
    private String toAddress;
    private double amount;
    private FeeLevel feeLevel;
    private double fee;
    private TransactionStatus status;
    private LocalDateTime creationDate;
    private CryptoType cryptoType;

    public Transaction(String from, String to, double amount, FeeLevel feeLevel, CryptoType type) {
        this.id = UUID.randomUUID().toString();
        this.fromAddress = from;
        this.toAddress = to;
        this.amount = amount;
        this.feeLevel = feeLevel;
        this.cryptoType = type;
        this.status = TransactionStatus.PENDING;
        this.creationDate = LocalDateTime.now();
        this.fee = calculateFee();
    }

	
	
	
	
	
	
	
	
	
	
	
	

}
