package com.wallet.repository;
import com.wallet.domain.Wallet;
import com.wallet.utils.CryptoType;

import com.wallet.utils.Config;

import java.sql.*;

public class WalletRepositoryImpl implements WalletRepository{
	
	 @Override
	    public void save(Wallet wallet) throws SQLException {
	        Connection conn = Config.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "INSERT INTO wallets(id, type, address, balance) VALUES (?,?,?,?)"
	        );
	        ps.setString(1, wallet.getId());
	        ps.setString(2, wallet.getType().name());
	        ps.setString(3, wallet.getAddress());
	        ps.setDouble(4, wallet.getBalance());
	        ps.executeUpdate();
	    }

	    @Override
	    public Wallet findById(String id) throws SQLException {
	        Connection conn = Config.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "SELECT * FROM wallets WHERE id = ?"
	        );
	        ps.setString(1, id);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	        	 CryptoType type = CryptoType.valueOf(rs.getString("type").toUpperCase());
	             return new Wallet(
	                 rs.getString("id"),
	                 rs.getString("address"),
	                 rs.getDouble("balance"),
	                 type
	             );
	         }
	         return null;
	     }

}
