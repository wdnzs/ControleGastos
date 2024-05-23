package controller;
import java.sql.*;

public class GastosController {
	
	public static Connection conector() {
		
		java.sql.Connection conexao = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/gastos?characterEncoding=utf-8";
		String user = "root";
		String password = "123456";
		
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, 
                                user, password);
			return conexao;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
			return null;
		}
		
		
	}

}
