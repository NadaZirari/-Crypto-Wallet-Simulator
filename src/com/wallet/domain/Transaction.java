package com.wallet.domain;

import com.wallet.utils.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
	
	  private UUID id;
	  private String fromAddress;
    private String toAddress;
    private double amount;
    private FeePriority feeLevel;
    private double fee;
    private TxStatus status;
    private LocalDateTime creationDate;
    private CryptoType cryptoType;

    public Transaction(String from, String to, double amount, FeePriority feeLevel, CryptoType type) {
    	 this.id = UUID.randomUUID();
        this.fromAddress = from;
        this.toAddress = to;
        this.amount = amount;
        this.feeLevel = feeLevel;
        this.cryptoType = type;
        this.status = TxStatus.PENDING;
        this.creationDate = LocalDateTime.now();
        this.fee = calculateFee();
    }
    public Transaction(UUID id, String from, String to, double amount, FeePriority feeLevel,
            double fee, TxStatus status, CryptoType type, LocalDateTime creationDate) {
this.id = id;
this.fromAddress = from;
this.toAddress = to;
this.amount = amount;
this.feeLevel = feeLevel;
this.fee = fee;
this.status = status;
this.cryptoType = type;
this.creationDate = creationDate;
}
	
    private double calculateFee() {
        double result = 0;
        switch (cryptoType) {
            case BITCOIN:
                int satoshiPerByte = 0;
                switch (feeLevel) {
                    case ECONOMIQUE: satoshiPerByte = Constants.BTC_FEE_ECONOMIQUE; break;
                    case STANDARD:   satoshiPerByte = Constants.BTC_FEE_STANDARD; break;
                    case RAPIDE:     satoshiPerByte = Constants.BTC_FEE_RAPIDE; break;
                }
                result = Constants.BTC_TX_SIZE * satoshiPerByte / 100_000_000.0;
                break;

            case ETHEREUM:
                int gasPrice = 0;
                switch (feeLevel) {
                    case ECONOMIQUE: gasPrice = Constants.ETH_GAS_ECONOMIQUE; break;
                    case STANDARD:   gasPrice = Constants.ETH_GAS_STANDARD; break;
                    case RAPIDE:     gasPrice = Constants.ETH_GAS_RAPIDE; break;
                }
                result = Constants.ETH_GAS_LIMIT * gasPrice / 1_000_000_000.0;
                break;
        }
        return result;
    }

    public UUID getId() { return id; }
    public String getFromAddress() { return fromAddress; }
    public String getToAddress() { return toAddress; }
    public double getAmount() { return amount; }
    public FeePriority getFeeLevel() { return feeLevel; }
    public double getFee() { return fee; }
    public TxStatus getStatus() { return status; }
    public void setStatus(TxStatus status) { this.status = status; }
    public void setFeePriority(FeePriority feeLevel) {
        this.feeLevel = feeLevel;
        this.fee = calculateFee();
    }
    public LocalDateTime getCreationDate() { return creationDate; }
    public CryptoType getCryptoType() { return cryptoType; }
}