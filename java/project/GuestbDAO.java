package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.Guestb;

/* GuestBook CRUD 기능 구현 부분 */
/* Get All GuestBook list(전체 조회) Add list (등록), Get list(목록 가져오기), Delete list(목록 삭제) Update list(목록 수정) */
public class GuestbDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~//jwbookdb";
	
	/* DB connect method */
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "jwbook","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
		/* Get all GuestBook list method */
		/* 방명록 목록(gb_main.html)에 방명록 리스트 전체 가져오기 */
		// Post GuestBook List to Controller (return "list type" )
		public List<Guestb> getAll() throws Exception {
			System.out.println("getAll start");
			// DB 연결
			Connection conn = open();
			List<Guestb> guestbList = new ArrayList<>();
			
			String sql = "SELECT id, username, email, PARSEDATETIME(date,'yyyy-MM-dd HH:mm:ss') as cdate, title, password, content FROM guestbook";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			// try(conn; pstmt): 예외 발생시 자동으로 close 하기에 catch문 불필요
			try(conn; pstmt; rs) {
				while(rs.next()) {
					Guestb g = new Guestb();
					g.setId(rs.getInt("id"));
					g.setUsername(rs.getString("username"));
					g.setEmail(rs.getString("email"));
					g.setDate(rs.getString("cdate"));
					g.setTitle(rs.getString("title"));
					g.setPassword(rs.getString("password"));
					g.setContent(rs.getString("content"));
					
					guestbList.add(g);
				}
				System.out.println("하하7");
				return guestbList;
			}
		}
		
		/* Show GuestBook List Detail content method */
		/* 방명록 목록(gb_main)에서 방명록을 누를때 해당 방명록의 세부 내용을 보여주는 메소드*/
		
		// 방명록 id 주키 가져오기 (Read)
		public Guestb getList(int id) throws SQLException {
			System.out.println("getList start");
			Connection conn = open();
			
			Guestb g = new Guestb();
			String sql = "SELECT id, username, email, PARSEDATETIME(date, 'yyyy-MM-dd HH:mm:ss') as cdate, title, password, content FROM guestbook WHERE id=?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			// getAll method와 비슷 but. 검색결과 하나이기에 next() 한번만 호출
			rs.next();
			
			try(conn; pstmt; rs){
				g.setId(rs.getInt("id"));
				g.setUsername(rs.getString("username"));
				g.setEmail(rs.getString("email"));
				g.setDate(rs.getString("cdate"));
				g.setTitle(rs.getString("title"));
				g.setPassword(rs.getString("password"));
				g.setContent(rs.getString("content"));
				pstmt.executeQuery();
				
				return g;
			}
		}
		
		/* Add GuestBook List method (방명록 추가하는 메서드) */
		// SQL part
		public void addList(Guestb g) throws Exception {
			System.out.println("addList start");
			Connection conn = open();
			
			String sql = "INSERT INTO guestbook(username, email, title, date, password, content) values(?,?,?,CURRENT_TIMESTAMP(0),?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// try(conn; pstmt): 예외 발생시 자동으로 close 하기에 catch문 불필요
			try(conn; pstmt){
				pstmt.setString(1, g.getUsername());
				pstmt.setString(2, g.getEmail());
				pstmt.setString(3, g.getTitle());
				pstmt.setString(4, g.getPassword());
				pstmt.setString(5, g.getContent());
					
				pstmt.executeUpdate();
			}
		}
			
		/* Update Guest Book List method */
		public void updateList(Guestb g) throws SQLException{
			Connection conn = open();
			
			String sql = "UPDATE guestbook SET username=?, email=?, title=?, password=?, content=? WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			try(conn; pstmt){
				pstmt.setString(1, g.getUsername());
				pstmt.setString(2, g.getEmail());
				pstmt.setString(3, g.getTitle());
				pstmt.setString(4, g.getPassword());
				pstmt.setString(5, g.getContent());
				pstmt.setInt(6, g.getId());
				
				pstmt.executeUpdate();
			}
		}	
			
		/* Delete Guest Book List method */
		/* 삭제할 방명록 id 받아서 방명록 삭제 */
		public void delList(int id) throws SQLException{
			System.out.println("delList start");
			Connection conn = open();
			
			String sql = "DELETE FROM guestbook WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			try(conn; pstmt){
				pstmt.setInt(1, id);
				// If GuestBook List isn't deleted
				if(pstmt.executeUpdate() == 0) {
					throw new SQLException("DB Error");
				}
			}
		}
		
		
}
