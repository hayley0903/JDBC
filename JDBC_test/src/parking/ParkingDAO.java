package parking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ParkingDAO {

	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;


	public int insertParking(int location, String carNum) {
		int resultChk = 0;

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


		try {

			String sql = "UPDATE tb_parking_info\r\n"
					+ "SET\r\n"
					+ "parking_yn = 'Y',\r\n"
					+ "parking_car_number = ?,\r\n"
					+ "parking_date = current_timestamp()\r\n"
					+ "WHERE parking_number = ?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, carNum);
			pstmt.setInt(2,location);
			resultChk = pstmt.executeUpdate();

		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return resultChk;

	}

	public int deleteParking(int location) {
		int resultChk = 0;

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


		try {

			String sql = "UPDATE tb_parking_info\r\n"
					+ "SET\r\n"
					+ "parking_yn = 'N',\r\n"
					+ "parking_car_number = NULL,\r\n"
					+ "parking_date = current_timestamp()\r\n"
					+ "WHERE parking_number = ?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,location);
			resultChk = pstmt.executeUpdate();

		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return resultChk;

	}

	public List<HashMap<String, Object>> selectParkingSpace() {

		List<HashMap<String, Object>> parkingList = new ArrayList();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}

		try {
			String sql = "SELECT  parking_idx AS parkingIdx,\r\n"
					+ "		parking_number AS parkingNumber,\r\n"
					+ "		parking_location_x AS parkingX, \r\n"
					+ "		parking_location_y AS parkingY,\r\n"
					+ "     parking_yn AS parkingYn,\r\n"
					+ "     parking_car_number AS parkingCarNumber\r\n"
					+ "FROM tb_parking_info\n"
					+ "ORDER BY parking_location_x, parking_location_y;";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();


			while(rs.next()) {
				HashMap<String, Object> parkingMap = new HashMap<String, Object>();
				parkingMap.put("parkingIdx", rs.getInt("parkingIdx"));
				parkingMap.put("parkingNumber", rs.getInt("parkingNumber"));
				parkingMap.put("parkingX", rs.getInt("parkingX"));
				parkingMap.put("parkingY", rs.getInt("parkingY"));
				parkingMap.put("parkingYn", rs.getString("parkingYn"));
				parkingMap.put("parkingCarNumber", rs.getString("parkingCarNumber"));

				parkingList.add(parkingMap);
			}

		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
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

			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return parkingList;

	}

	public HashMap<String, Object> selectParking(int location) {

		HashMap<String, Object> parkingMap = new HashMap<String, Object>();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}

		try {
			String sql = "SELECT  parking_number,\r\n"
					+ "		parking_yn,\r\n"
					+ "        parking_car_number\r\n"
					+ "		FROM tb_parking_info\r\n"
					+ "        where parking_number = ?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, location);

			rs = pstmt.executeQuery();


			while(rs.next()) {
				parkingMap.put("parkingNumber", rs.getInt("parking_number"));
				parkingMap.put("parkingYn", rs.getString("parking_yn"));
				parkingMap.put("parkingCarNumber", rs.getString("parking_car_number"));
			}	

		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
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

			}catch(SQLException e) {
				e.printStackTrace();
			}


		}
		return parkingMap;

	}

	public int parkingHistory(int location, String carNum, String InOut) {
		
		
		int resultChk = 0;

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

		try {
			String sql = "INSERT INTO tb_parking_history\r\n"
					+ "(\r\n"
					+ "parking_checkinout,\r\n"
					+ "parking_number,\r\n"
					+ "car_number,\r\n"
					+ "parking_time\r\n"
					+ ")\r\n"
					+ "VALUES\r\n"
					+ "(?,?,?, current_timestamp());";


			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, InOut);
			pstmt.setInt(2, location);
			pstmt.setString(3, carNum);


			resultChk = pstmt.executeUpdate();

		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}


		return resultChk;
	}



}
