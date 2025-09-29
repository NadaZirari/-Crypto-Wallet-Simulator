package com.wallet.repository;

import com.wallet.utils.FeePriority;
import com.wallet.utils.CryptoType;
import com.wallet.utils.TxStatus;

import com.wallet.domain.Transaction;
import com.wallet.utils.Config;

import java.sql.*;

public class TransactionRepositoryImpl implements TransactionRepository {
	
	
	
	
	 @Override
	    public void save(Transaction tx) throws SQLException {
	        Connection conn = Config.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "INSERT INTO transactions(id, from_address, to_address, amount, fee_level, fee, status, crypto_type, creation_date) VALUES (?,?,?,?,?,?,?,?,?)"
	        );
	        ps.setString(1, tx.getId());
	        ps.setString(2, tx.getFromAddress());
	        ps.setString(3, tx.getToAddress());
	        ps.setDouble(4, tx.getAmount());
	        ps.setString(5, tx.getFeeLevel().name());
	        ps.setDouble(6, tx.getFee());
	        ps.setString(7, tx.getStatus().name());
	        ps.setString(8, tx.getCryptoType().name());
	        ps.setTimestamp(9, Timestamp.valueOf(tx.getCreationDate()));
	        ps.executeUpdate();
	    }

	    @Override
	    public Transaction findById(String id) throws SQLException {
	        Connection conn = Config.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "SELECT * FROM transactions WHERE id = ?"
	        );
	        ps.setString(1, id);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            Transaction tx = new Transaction(
	                rs.getString("from_address"),
	                rs.getString("to_address"),
	                rs.getDouble("amount"),
	                Enum.valueOf(FeePriority.class, rs.getString("fee_level")),
	                Enum.valueOf(CryptoType.class, rs.getString("crypto_type"))
	            );
	            tx.setStatus(Enum.valueOf(TxStatus.class, rs.getString("status")));
	            return tx;
	        }
	        return null;
	    }
	

}
