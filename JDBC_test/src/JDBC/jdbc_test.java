package JDBC;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbc_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String driver = "org.mariadb.jdbc.Driver";
		final String db_ip = "localhost";
		final String db_port = "3306";
		final String db_name = "student_test";
		final String db_url = "jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
		
		
		//JDBC connection --> import mysql 
		Connection conn = null;
		
		//DB접속실행
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속완료");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
					
		}catch(SQLException e) {
			System.out.println("DB접속 실패");
			e.printStackTrace();
		}
		
		

	}
}
