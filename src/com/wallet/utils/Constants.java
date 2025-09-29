package com.wallet.utils;

public class Constants {

	// Bitcoin fees en satoshi/byte
    public static final int BTC_FEE_ECONOMIQUE = 1;
    public static final int BTC_FEE_STANDARD = 5;
    public static final int BTC_FEE_RAPIDE = 10;
    public static final int BTC_TX_SIZE = 250; // bytes moyenne

    // Ethereum fees (gas price en gwei)
    public static final int ETH_GAS_ECONOMIQUE = 20;
    public static final int ETH_GAS_STANDARD = 50;
    public static final int ETH_GAS_RAPIDE = 100;
    public static final int ETH_GAS_LIMIT = 21000; // gas moyen

    // Estimation temps de confirmation
    public static final int TIME_PER_POSITION_MIN = 10; // minutes
    
}
