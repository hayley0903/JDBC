package book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BookDAO {

	//DB접속
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	//정상적으로 실행시(데이터의 변동이 있을시) 데이터 변화가 한번일때 1, 세번일때 3//데이터 변동이 없을시 반환값은 0
	public int insertBook(BookInfo bookInfo) {

		int resultChk = 0;

		//DB접속
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

		//접속 후 쿼리실행 --> 도서정보등록
		try {
			String sql = "INSERT INTO `jdbc_test`.`tb_book_info`\r\n"
					+ "(\r\n"
					+ "`book_title`,\r\n"
					+ "`book_price`,\r\n"
					+ "`book_author`,\r\n"
					+ "`book_publisher`,\r\n"
					+ "`book_pubYear`,\r\n"
					+ "`book_isbn`,\r\n"
					+ "`book_page`\r\n"
					+ ")\r\n"
					+ "VALUES\r\n"
					+ "(\r\n"
					+ "?,?,?,?,?,?,?\r\n"
					+ ");";


			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bookInfo.getTitle());
			pstmt.setInt(2, bookInfo.getPrice());
			pstmt.setString(3, bookInfo.getAuthor());
			pstmt.setString(4, bookInfo.getPublisher());
			pstmt.setString(5, bookInfo.getPubYear());
			pstmt.setString(6, bookInfo.getIsbn());
			pstmt.setInt(7, bookInfo.getPage());


			//결과를 담을때 select문의 경우 (조회만 할떄는) executeQuery();를 사용
			//rs = pstmt.executeQuery();

			//insert문일경우 executeUpdate()를 사용하여 결과를 담고 실행
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

	public List<HashMap<String, Object>> printAllBooks(){
		List<HashMap<String, Object>> bookList = new ArrayList();

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
			String sql = "SELECT\r\n"
					+ "	book_id,\r\n"
					+ "	book_title,\r\n"
					+ "	book_price,\r\n"
					+ "	book_author,\r\n"
					+ "	book_publisher,\r\n"
					+ "    book_pubYear,\r\n"
					+ "    book_isbn,\r\n"
					+ "    book_page,\r\n"
					+ "    create_date,\r\n"
					+ "    update_date\r\n"
					+ "FROM tb_book_info;";


			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();


			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("book_Id", rs.getInt("book_id"));
				rsMap.put("book_title", rs.getString("book_title"));
				rsMap.put("book_price", rs.getInt("book_price"));
				rsMap.put("book_author", rs.getString("book_author"));
				rsMap.put("book_publisher", rs.getString("book_publisher"));
				rsMap.put("book_pubYear", rs.getString("book_pubYear"));
				rsMap.put("book_isbn", rs.getString("book_isbn"));
				rsMap.put("book_page", rs.getInt("book_page"));
				rsMap.put("create_date", rs.getTimestamp("create_date"));
				rsMap.put("update_date", rs.getTimestamp("update_date"));
				bookList.add(rsMap);
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
		return bookList;

	}

	public List<HashMap<String, Object>> printBook(String title){
		List<HashMap<String, Object>> bookList = new ArrayList();

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
			String sql = "SELECT\r\n"
					+ "	book_id,\r\n"
					+ "	book_title,\r\n"
					+ "	book_price,\r\n"
					+ "	book_author,\r\n"
					+ "	book_publisher,\r\n"
					+ "    book_pubYear,\r\n"
					+ "    book_isbn,\r\n"
					+ "    book_page,\r\n"
					+ "    create_date,\r\n"
					+ "    update_date\r\n"
					+ "FROM tb_book_info\r\n"
					+ "where book_title like concat('%', ?, '%');";
		

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);

			rs = pstmt.executeQuery();


			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("book_Id", rs.getInt("book_id"));
				rsMap.put("book_title", rs.getString("book_title"));
				rsMap.put("book_price", rs.getInt("book_price"));
				rsMap.put("book_author", rs.getString("book_author"));
				rsMap.put("book_publisher", rs.getString("book_publisher"));
				rsMap.put("book_pubYear", rs.getString("book_pubYear"));
				rsMap.put("book_isbn", rs.getString("book_isbn"));
				rsMap.put("book_page", rs.getInt("book_page"));
				rsMap.put("create_date", rs.getTimestamp("create_date"));
				rsMap.put("update_date", rs.getTimestamp("update_date"));
				bookList.add(rsMap);
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
		return bookList;
	}


	public int removeBook(String title) {

		int resultChk = 0;

		//DB접속
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

		//접속 후 쿼리실행 --> 도서정보등록
		try {
			String sql = "delete from tb_book_info where book_title = ?;";


			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);


			//insert문일경우 executeUpdate()를 사용하여 결과를 담고 실행
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
	
	public int removeOneBook(int bookId) {

		int updateChk = 0;

		//DB접속
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

		//접속 후 쿼리실행 --> 도서정보등록
		try {
			String sql = "delete from tb_book_info where book_id = ?;";


			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bookId);


			//insert문일경우 executeUpdate()를 사용하여 결과를 담고 실행
			updateChk = pstmt.executeUpdate();

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

		return updateChk;
	}

	public int updateBook(int bookId, String updateTitle) {

		int updateChk = 0;

		//DB접속
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

		//접속 후 쿼리실행 --> 도서정보등록
				try {
					String sql = "UPDATE tb_book_info\r\n"
							+ "SET book_title = ?\r\n"
							+ "WHERE book_id = ?;";


					pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, updateTitle);
					pstmt.setInt(2, bookId);

					//결과를 담을때 select문의 경우 (조회만 할떄는) executeQuery();를 사용
					//rs = pstmt.executeQuery();

					//insert문일경우 executeUpdate()를 사용하여 결과를 담고 실행
					updateChk = pstmt.executeUpdate();

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

		return updateChk;
	}
	
	
	
}
