package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDAO {
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 학생 정보 등록
	public int insertStudent(HashMap<String, Object> paramMap) {
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
			String sql = "INSERT INTO tb_student_info\n"
					+ "(student_name,\n"
					+ "student_grade,\n"
					+ "student_school,\n"
					+ "student_addr,\n"
					+ "student_phone)\n"
					+ "VALUES\n"
					+ "(?,?,?,?,?);";


			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paramMap.get("student_name").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("student_grade").toString()));
			pstmt.setString(3, paramMap.get("student_school").toString());
			pstmt.setString(4, paramMap.get("student_addr").toString());
			pstmt.setString(5, paramMap.get("student_phone").toString());


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
	
	// 학생 성적 등록
	public int insertScore(HashMap<String, Object> paramMap) {
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
			String sql = "INSERT INTO tb_student_score\n"
					+ "(\n"
					+ "score_season,\n"
					+ "score_semester,\n"
					+ "score_exam_type,\n"
					+ "score_subject,\n"
					+ "score_point,\n"
					+ "student_idx)\n"
					+ "VALUES\n"
					+ "(\n"
					+ "?,?,?,?,?,?);";


			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paramMap.get("season").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("semester").toString()));
			pstmt.setString(3, paramMap.get("examType").toString());
			pstmt.setString(4, paramMap.get("subject").toString());
			pstmt.setInt(5, Integer.parseInt(paramMap.get("point").toString()));
			pstmt.setInt(6, Integer.parseInt(paramMap.get("student_idx").toString()));

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
		
	// 전체 학생 조회
	public List<HashMap<String, Object>> printAllStudent(){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
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
			String sql = "select T1.student_idx,\n"
					+ "T1.student_name,\n"
					+ "T1.student_grade,\n"
					+ "T1.student_school,\n"
					+ "T1.student_addr,\n"
					+ "T1.student_phone,\n"
					+ "T2.score_season,\n"
					+ "T2.score_semester,\n"
					+ "T2.score_exam_type,\n"
					+ "T2.score_subject,\n"
					+ "T2.score_point\n"
					+ " from tb_student_info T1\n"
					+ "inner join tb_student_score T2\n"
					+ "ON T1.student_idx = T2.student_idx;";


			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();


			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("student_idx", rs.getInt("T1.student_idx"));
				rsMap.put("student_name", rs.getString("T1.student_name"));
				rsMap.put("student_grade", rs.getInt("T1.student_grade"));
				rsMap.put("student_school", rs.getString("T1.student_school"));
				rsMap.put("student_addr", rs.getString("T1.student_addr"));
				rsMap.put("student_phone", rs.getString("T1.student_phone"));
				rsMap.put("score_season", rs.getString("T2.score_season"));
				rsMap.put("score_semester", rs.getString("T2.score_semester"));
				rsMap.put("score_exam_type", rs.getString("T2.score_exam_type"));
				rsMap.put("score_subject", rs.getString("T2.score_subject"));
				rsMap.put("score_point", rs.getString("T2.score_point"));
				
				studentList.add(rsMap);
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
		
		return studentList;
	}
	
	
	// 학생이름으로 학생 정보 조회
		public List<HashMap<String, Object>> printSearchStudent(String studentName){
			List<HashMap<String, Object>> studentList = new ArrayList();
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
				String sql = "SELECT student_idx,\n"
						+ "    student_name,\n"
						+ "    student_grade,\n"
						+ "   student_school,\n"
						+ "    student_addr,\n"
						+ "    student_phone\n"
						+ "FROM tb_student_info\n"
						+ "where student_name = ?;";


				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, studentName);

				rs = pstmt.executeQuery();


				while(rs.next()) {
				
					HashMap<String, Object> rsMap = new HashMap<String, Object>();
					rsMap.put("student_idx", rs.getInt("student_idx"));
					rsMap.put("student_name", rs.getString("student_name"));
					rsMap.put("student_grade", rs.getInt("student_grade"));
					rsMap.put("student_school", rs.getString("student_school"));
					rsMap.put("student_addr", rs.getString("student_addr"));
					rsMap.put("student_phone", rs.getString("student_phone"));

					studentList.add(rsMap);
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

			
			return studentList;
		}
		
		// 학생이름으로 학생 성적 조회
				public List<HashMap<String, Object>> printSearchScore(int studentIdx){
					List<HashMap<String, Object>> studentList = new ArrayList();
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
						String sql = "select \n"
								+ "T2.student_name,\n"
								+ "T1.student_idx,\n"
								+ "T1.score_season,\n"
								+ "T1.score_semester,\n"
								+ "T1.score_exam_type,\n"
								+ "T1.score_subject,\n"
								+ "T1.score_point\n"
								+ "from tb_student_score T1\n"
								+ "inner join tb_student_info T2\n"
								+ "on T1.student_idx = T2.student_idx\n"
								+ "where T2.student_idx = ?;";


						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, studentIdx);

						rs = pstmt.executeQuery();


						while(rs.next()) {
						
							HashMap<String, Object> rsMap = new HashMap<String, Object>();
							rsMap.put("student_name", rs.getString("T2.student_name"));
							rsMap.put("student_idx", rs.getInt("T1.student_idx"));
							rsMap.put("score_season", rs.getInt("T1.score_season"));
							rsMap.put("score_semester", rs.getString("T1.score_semester"));
							rsMap.put("score_exam_type", rs.getString("T1.score_exam_type"));
							rsMap.put("score_subject", rs.getString("T1.score_subject"));
							rsMap.put("score_point", rs.getInt("T1.score_point"));

							studentList.add(rsMap);
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

					
					return studentList;
				}
	
	// 학생이름과 성적 학생 정보 조회
	public List<HashMap<String, Object>> printStudentScore(String studentName){
		List<HashMap<String, Object>> studentList = new ArrayList();
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
			String sql = "select T1.student_idx,\n"
					+ "T1.student_name,\n"
					+ "T1.student_grade,\n"
					+ "T1.student_school,\n"
					+ "T1.student_addr,\n"
					+ "T1.student_phone,\n"
					+ "T2.score_season,\n"
					+ "T2.score_semester,\n"
					+ "T2.score_exam_type,\n"
					+ "T2.score_subject,\n"
					+ "T2.score_point,\n"
					+ "T2.score_idx\n"
					+ " from tb_student_info T1\n"
					+ "inner join tb_student_score T2\n"
					+ "ON T1.student_idx = T2.student_idx\n"
					+ "where student_name = ?;";


			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentName);

			rs = pstmt.executeQuery();


			while(rs.next()) {
			
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("student_idx", rs.getInt("T1.student_idx"));
				rsMap.put("student_name", rs.getString("T1.student_name"));
				rsMap.put("student_grade", rs.getInt("T1.student_grade"));
				rsMap.put("student_school", rs.getString("T1.student_school"));
				rsMap.put("student_addr", rs.getString("T1.student_addr"));
				rsMap.put("student_phone", rs.getString("T1.student_phone"));
				rsMap.put("score_season", rs.getString("T2.score_season"));
				rsMap.put("score_semester", rs.getString("T2.score_semester"));
				rsMap.put("score_exam_type", rs.getString("T2.score_exam_type"));
				rsMap.put("score_subject", rs.getString("T2.score_subject"));
				rsMap.put("score_point", rs.getString("T2.score_point"));
				rsMap.put("score_idx", rs.getString("T2.score_idx"));
				

				studentList.add(rsMap);
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

		
		return studentList;
	}
	
	
	
	// 학생 정보 수정
	public int updateStudent(HashMap<String, Object> paramMap) {
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
			
			int updateChoice = Integer.parseInt(paramMap.get("updateChoice").toString());
			String sql = "";
	
			if(updateChoice == 1) {
				sql = "UPDATE tb_student_score\n"
						+ "SET\n"
						+ "score_season = ?\n"
						+ "WHERE score_idx = ?;";
			}else if (updateChoice == 2) {
				sql = "UPDATE tb_student_score\n"
						+ "SET\n"
						+ "score_semester = ?\n"
						+ "WHERE score_idx = ?;";
				
			}else if (updateChoice ==3) {
				sql = "UPDATE tb_student_score\n"
						+ "SET\n"
						+ "score_examType = ?\n"
						+ "WHERE score_idx = ?;";
			}else if(updateChoice ==4) {
				sql = "UPDATE tb_student_score\n"
						+ "SET\n"
						+ "score_subject = ?\n"
						+ "WHERE score_idx = ?;";
			} else {
				sql = "UPDATE tb_student_score\n"
						+ "SET\n"
						+ "score_point = ?\n"
						+ "WHERE score_idx = ?;";
			}
			
//			switch (updateChoice) {
//			
//			case 1 :
//				sql = "UPDATE tb_student_info\n"
//						+ "SET\n"
//						+ "student_name = ?\n"
//						+ "WHERE student_idx = ?;";
//				break;
//			case 2 :
//				sql = "UPDATE tb_student_info\n"
//						+ "SET\n"
//						+ "student_school = ?\n"
//						+ "WHERE student_idx = ?;";
//				break;
//			case 3 : 
//				sql = "UPDATE tb_student_info\n"
//						+ "SET\n"
//						+ "student_grade = ?\n"
//						+ "WHERE student_idx = ?;";
//				break;
//			case 4 : 
//				sql = "UPDATE tb_student_info\n"
//						+ "SET\n"
//						+ "student_phone = ?\n"
//						+ "WHERE student_idx = ?;";
//				break;
//			case 5 : 
//				sql = "UPDATE tb_student_info\n"
//						+ "SET\n"
//						+ "student_addr = ?\n"
//						+ "WHERE student_idx = ?;";
//				break;
//				
//			default:
//				break;
//			}

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,paramMap.get("updateContents").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("scoreIdx").toString()));
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
	
	// 학생 성적 수정
	public int updateScore(HashMap<String, Object> paramMap) {
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
			
			String sql = "UPDATE tb_student_info T1\n"
					+ "INNER JOIN tb_student_score T2\n"
					+ "on T1.student_idx = T2.student_idx\n"
					+ "SET T2.score_point = ?\n"
					+ "where T2.score_idx = ?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,Integer.parseInt(paramMap.get("updateScore").toString()));
			pstmt.setInt(2, Integer.parseInt(paramMap.get("scoreIdx").toString()));
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
	
	// 학생 정보 삭제
	public int deleteStudent(int studentIdx) {
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
			String sql = "delete from tb_student_info where student_idx = ?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentIdx);


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
	
	// 학생 성적 삭제
	public int deleteScore(HashMap<String, Object> paramMap) {
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
			String sql = "delete from tb_student_score \n"
					+ "where score_season = ?\n"
					+ "and score_semester = ?\n"
					+ "and score_exam_type = ?\n"
					+ "and score_subject = ?\n"
					+ "and student_idx = ?;";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paramMap.get("season").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("semester").toString()));
			pstmt.setString(3, paramMap.get("examType").toString());
			pstmt.setString(4, paramMap.get("subject").toString());
			pstmt.setInt(5, Integer.parseInt(paramMap.get("studentIdx").toString()));
			

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
