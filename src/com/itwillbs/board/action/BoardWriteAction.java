package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardWriteAction implements Action{
	//Pro 페이지의 기능을 하게

	
	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		// 이 메서드 안에서 실행하게 한다 - request, response를 받아와야 하므로
		
		System.out.println("M : BoardWriteAction.execute() 호출");
		// M은 모듈이라는 뜻
		
	
		//한글 처리
		request.setCharacterEncoding("UTF-8");
		//전달정보 저장(제목, 비밀번호, 이름, 내용)
		//BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		//ip주소 추가
		dto.setIp(request.getRemoteAddr()); // 유저정보를 확인할 수 있는 
		
		
		System.out.println("M : " + dto);
		
		//DB에 정보 저장
		//BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		//DB에 글정보를 저장
		dao.boardWrite(dto);
		
		//페이지 이동정보 저장(리턴) - 페이지 이동은 컨트롤러에서 움직인다
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo"); // 게시판 리스트로 이동
		forward.setRedirect(true); 
		// true-sendRedirect()-주소,화면 모두바뀜 / false-forward()-주소는 안바뀜, 화면만 바뀐다
		//상황에 따라서 결정한ㄷ
		
		return forward;
	}
	
}
