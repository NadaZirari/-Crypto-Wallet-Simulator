package com.wallet.repository;
import com.wallet.domain.Wallet;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;



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
	    ps.setObject(1, wallet.getId());  // ← UUID directement
	    ps.setString(2, wallet.getType().name());
	    ps.setString(3, wallet.getAddress());
	    ps.setDouble(4, wallet.getBalance());
	    ps.executeUpdate();
	}

	@Override
	public Wallet findById(UUID id) throws SQLException {  // attention : UUID en param
	    Connection conn = Config.getConnection();
	    PreparedStatement ps = conn.prepareStatement(
	        "SELECT * FROM wallets WHERE id = ?"
	    );
	    ps.setObject(1, id);  // UUID directement
	    ResultSet rs = ps.executeQuery();

	    if (rs.next()) {
	        CryptoType type = CryptoType.valueOf(rs.getString("type").toUpperCase());
	        return new Wallet(
	        		rs.getObject("id", UUID.class) ,// ✅ Correct

	            rs.getString("address"),
	            rs.getDouble("balance"),
	            type
	        );
	    }
	    return null;
	}

	    @Override
	    public List<Wallet> findAll() throws SQLException {
	        Connection conn = Config.getConnection();
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM wallets");

	        List<Wallet> wallets = new ArrayList<>();
	        while (rs.next()) {
	            CryptoType type = CryptoType.valueOf(rs.getString("type").toUpperCase());
	            Wallet wallet = new Wallet(
	            		rs.getObject("id", UUID.class),
	            		rs.getString("address"),
	                rs.getDouble("balance"),
	                type
	            );
	            wallets.add(wallet);
	        }
	        return wallets;
	    }

}
