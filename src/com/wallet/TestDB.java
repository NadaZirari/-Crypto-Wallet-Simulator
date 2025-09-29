package com.wallet;

import com.wallet.utils.Config;
import java.sql.Connection;

public class TestDB {

	public static void main(String[] args) {
		try {
            Connection conn = Config.getConnection();
            if (conn != null) {
                System.out.println("Connexion OK !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
