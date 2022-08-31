package com.itwillbs.board.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BoardDAO {
	//DAO ( data access object)  : 데이터 처리 객체
	
	// 공통변수 (인스턴스 변수)
	private Connection con = null; // 디비연결정보 저장
	private PreparedStatement pstmt = null; // 디비에 sql 실행 처리 객체
	private ResultSet rs = null; // select 실행 결과 저장 객체
	private String sql = ""; // sql 쿼리 구문 저장
	
	
	public BoardDAO() {	
		System.out.println("DAO : DB연결에 관한 모든 정보를 준비 완료!");
	}
	
//	//디비 연결
//	private Connection getConnect() throws Exception{
//		//디비 연결 정보
//		String DRIVER = "com.mysql.cj.jdbc.Driver";
//		String DBURL = "jdbc:mysql://localhost:3306/jspdb";
//		String DBID = "root";
//		String DBPW = "1234";
//				
//		//1. 드라이버 로드
//		Class.forName(DRIVER);
//		System.out.println("드라이버로드 성공");
//				
//		//2. 디비연결
//		con = DriverManager.getConnection(DBURL, DBID, DBPW);
//		System.out.println("디비 연결 성공");
//		System.out.println("con : " + con);
//				
//		return con;		
//	
//	}// 디비연결
	
	//디비 연결
	private Connection getConnect() throws Exception{
		//디비 연결 정보 - context.xml
		
		//프로젝트 정보를 초기화
		Context initCTX = new InitialContext();
			
		//초기화된 프로젝트 정보 중 데이터 불러오기
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/model12");
		
		//연결된 정보를 바탕으로 connection정보를 리턴
		con = ds.getConnection();
				
		return con;		
	
	}// 디비연결
	
	
	// 자원 해제
	public void closeDB(){
		try {
			// 역순으로 자원을 닫아준다
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
			
			System.out.println(" DAO : 자원해제 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// 자원 해제
	
	
	// 글쓰기 - boardWrite()
	public void boardWrite(BoardDTO dto){
		int bno = 0; // 글번호 저장
		try {
			//1. 드라이버 로드
			//2. 디비 연결
			con = getConnect();
			
			//3. sql 작성 & pstmt 객체
			// 게시판 글번호 (bno) 계산
			// count(bno)=> 글이 삭제가 되면 중복된 글번호가 생성될 수 있다
			// 글번호는 작성된 가장 마지막 글번호 +1
			sql = "select max(bno) from itwill_board";
			pstmt = con.prepareStatement(sql);
			//4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리(글번호 계산)가장 마지막 글번호 +1
			if(rs.next()){
				//bno = rs.getInt("max(bno)")+1;
				bno = rs.getInt(1)+1; // 인덱스사용
				//getInt메서드 자체가 null일때 0을 리턴해줘서 
				//굳이 첫글일때를 지정해주지 않아도 계산된다
				//getInt() => 컬럼의 값을 리턴, 만약에 값이 sql-null결우 0 리턴
			}
			//else{ 생략한다
			//	bno = 1; // 데이터가 없으면 1로 지정
				//max(bno) bno의 차이는 뭘까
				// 둘다 null이지만
				//max(bno)는 커서가 있어서 rs.next는 true다
				//bno는 동그라미 커서가 없다 rs.next가 false이다
			//}
			
			System.out.println(" DAO : 글번호 bno : " + bno);
			
			// 게시판 글 쓰기
			// 3.sql작성 & pstmt  객체
			//date는 now() 함수를 사용해준다
			sql = "insert into itwill_board(bno, name, pass, subject, content,"
					+ "readcount, re_ref, re_lev, re_seq,date,ip,file) "
					+ "values(?,?,?,?,?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(sql);
			
			//???
			pstmt.setInt(1, bno);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getPass());
			pstmt.setString(4, dto.getSubject());
			pstmt.setString(5, dto.getContent());
			
			pstmt.setInt(6, 0);//조회수는 항상 0
			pstmt.setInt(7, bno); // 답글 그룹번호 == 글번호
			pstmt.setInt(8, 0); // 답글 레벨 0(일반글)
			pstmt.setInt(9, 0); // 답글 순서 0(일반글)
			
			pstmt.setString(10, dto.getIp()); // date는 now써서 바로 ip
			pstmt.setString(11, dto.getFile());
			
			//4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 글 작성 완료 ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
	
	}// 글쓰기 - boardWrite()
	
	
	// 글 목록 조회(all) - getBoardList()
	// BoardDTO를 여러개 저장해 올 수 있는 배열을 반환타입으로 한다
	public List<BoardDTO> getBoardList(){
		
		//글정보 모두를 저장하는 배열(가변길이)
		List<BoardDTO> boardList = new ArrayList<>();
		
		try {
			// 1. 드라이버로드
			// 2. 디비연결
			con = getConnect();
			
			// 3. sql 작성 & pstmt 객체
			sql = "select * from itwill_board";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			while(rs.next()){
				//데이터 있을 때 DB에 저장된 정보를 DTO저장 -> List 저장
				
				//DB정보를 -> DTO에 저장
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("date"));
				dto.setFile(rs.getString("file"));
				dto.setIp(rs.getString("ip"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setSubject(rs.getString("subject"));
				
				//DTO -> List
				boardList.add(dto);
				
			}//while
			
			System.out.println(" DAO : 게시판 목록 모두 저장 완료 ");
//			System.out.println(" DAO : " + boardList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeDB();
		}

		return boardList;
	}// 글 목록 조회(all) - getBoardList()
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
} // class
