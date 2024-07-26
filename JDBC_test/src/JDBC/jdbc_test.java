package JDBC;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		//prepareStatement = 실행될 쿼리를 담을 값을 변수
		PreparedStatement pstmt = null;
		//resultSet = 쿼리 결과값을 담을 변수
		ResultSet rs = null;

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

		//쿼리
		try {
			String sql = "SELECT school_id, school_name, school_area "
					+ "FROM tb_school_info "
					+ "WHERE school_id =1";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			//(try) 쿼리의 결과값을 담을 변수 설정
			int schoolID = 0;
			String schoolName = null;
			String schoolArea = null;

			while(rs.next()) {
				schoolID = rs.getInt(1);
				schoolName = rs.getString("school_name");
				schoolArea = rs.getString(3);
			}

			System.out.println("schoolID : "+ schoolID);
			System.out.println("schoolName : "+ schoolName);
			System.out.println("schoolArea : "+ schoolArea);

		}catch(SQLException e) {
			//TODO: handel exception
			System.out.println("Error: " + e);

		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		  }
		}





	}

