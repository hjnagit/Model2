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
		// 이 프로젝트를 초기화한다는
		Context initCTX = new InitialContext();
			
		//초기화된 프로젝트 정보 중 데이터 불러오기
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/model2");
		
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
		System.out.println("\n DAO : boardWrite(dto) 호출");
		
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
	
	
	// 글 목록 조회(all) - getBoardList() - 전체를 다 들고오는
	// BoardDTO를 여러개 저장해 올 수 있는 배열을 반환타입으로 한다
	public List<BoardDTO> getBoardList(){
		
		System.out.println("\n DAO : getBoardList() 호출");
		
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
	
	
	
	// 글 목록 조회(all) - getBoardList(int startRow, int pageSize) - 오버로딩
	// 원하는 만큼만 들고오는
	public List<BoardDTO> getBoardList(int startRow, int pageSize){
		
		System.out.println("\n DAO : getBoardList(int startRow, int pageSize) 호출");
		
		//글정보 모두를 저장하는 배열(가변길이)
		List<BoardDTO> boardList = new ArrayList<>();
		
		try {
			// 1. 드라이버로드
			// 2. 디비연결
			con = getConnect();
			
			// 3. sql 작성 & pstmt 객체
//			sql = "select * from itwill_board";
			
			// limit 시작행-1, 개수 : 시작 지점부터 해당 개수만큼 잘라오기 
			// 정렬 re_ref 내림차순, re_seq 오름차순 -> 최신글이 가장 먼저오게
			sql = "select * from itwill_board order by re_ref desc, re_seq asc limit ?, ?";
			
			pstmt = con.prepareStatement(sql);
			
			//???
			pstmt.setInt(1, startRow); // 시작행-1
			pstmt.setInt(2, pageSize); // 몇개씩 보여줄 것인지
			
			
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
	}// 글 목록 조회(all) - getBoardList(int startRow, int pageSize)
	
	
	
	// 글 전체 개수 조회(all) - getBoardCount()
	public int getBoardCount(){
		System.out.println("\n DAO : getBoardCount() 호출");
		int cnt = 0;
		
		try {
			// 1.2. 디비 연결 ( 커넥션 풀)
			con = getConnect();
			
			// 3. sql작성 & pstmt 객체
			sql = "select count(*) from itwill_board";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행 - select
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()){
				//데이터 있을 때
				//cnt = rs.getInt("count(*)"); // 컬럼명이 count(*)
				cnt = rs.getInt(1); // 인덱스 명을 사용한다
				
			}
			
			System.out.println("DAO : 글 개수 - 총 : " + cnt + "개");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{ 
			// 무조건 적어야한다 - 그래서 그냥 처음에 바로 적는다
			closeDB();
			//자동으로 자원해제를 해주는 구문 try-with구문
		}
		
		
		return cnt; // 리턴 꼭 해야한다 까먹지말고
		
	}// 글 전체 개수 조회(all) - getBoardCount()
	
	
	
	// 조회수 1증가시키기 updateReadcount()
	public void updateReadcount(int bno){
		System.out.println("C : updateReadcount() 호출");
		
		try {
			//12디비연결
			con = getConnect();
			
			//3 sql 작성 & pstmt 객체
			sql = "update itwill_board set readcount=readcount+1 where bno = ?";
			pstmt = con.prepareStatement(sql);
			
			//???
			pstmt.setInt(1, bno);
			
			//4 sql  실행
			pstmt.executeUpdate();
			
			System.out.println("DAO : 게시판글 조회수 1증가 완료");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
		
		
	}// 조회수 1증가시키기 updateReadcount()
	
	
	
	// 특정글 1개의 정보 조회 -  getBoard(bno)
	public BoardDTO getBoard(int bno){
		System.out.println("DAO : getBoard(bno) 호출");
		
		BoardDTO dto = null;
		
		try {
			//12 디비연결
			con = getConnect();
			
			//3 sql작성select & pstmt 객체
			sql = "select * from itwill_board where bno = ?";
			pstmt = con.prepareStatement(sql);
			
			//??
			pstmt.setInt(1, bno);
			
			//4 sql 실행
			rs = pstmt.executeQuery();
			
			//5 데이터처리
			if(rs.next()){
				// DB에 특정 번호의 글번호를 저장
				
				//DB -> DTB
				//테이블의 정보를 전부 다 들고오는 과정은 필수적이다
				//지금 당장 사용하지 않더라도 들고오자
				dto = new BoardDTO();
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
				
				
			}// if
			
			System.out.println("DAO : 게시글" + bno + "번 게시글 정보 저장 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
		
		
		return dto;
		
	}// 특정글 1개의 정보 조회 -  getBoard(bno)
	
	
	
	//글 정보 수정하기 updateBoard(dto)
	public int updateBoard(BoardDTO dto){
		int result = -1;
		//0 1 -1 
		
		try {
			//1.2. 디비연결
			con = getConnect();
			
			
			//3. sql & pstmt
			//글의 비밀번호 가져오기
			sql = "select pass from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			
			//?
			pstmt.setInt(1, dto.getBno());
			
			
			//4. sql 실행
			rs = pstmt.executeQuery();
			
			
			//5.데이터 처리
			if(rs.next()){
				//게시판 글 있음
				if(dto.getPass().equals(rs.getString("pass"))){
					//비밀번호가 일치함
					//업데이트하기
					//3. sql -  update$pstmt객체
					sql = "update itwill_board set name=?, subject=?, content=? where bno=?";
					pstmt = con.prepareStatement(sql);
					
					//????
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getSubject());
					pstmt.setString(3, dto.getContent());
					pstmt.setInt(4, dto.getBno());
					
					//4. sql실행
					result = pstmt.executeUpdate();
					
					
				}else{
					//비밀번호가 다름
					result = 0;
				}
			}else{
				//게시판 글 없음
				result = -1;
			}
			
			System.out.println("DAO : 글 수정 완료(" + result + ")");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		
		return result;
	}//글 정보 수정하기 updateBoard(dto)
	
	
	
	
} // class
