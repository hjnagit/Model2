package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardReWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("M : BoardReWriteAction_execute 호출");
		
		//한글처리
		request.setCharacterEncoding("utf-8");
		
		//전달된 정보 저장
		//(bno, re_ref, re_lev, re_seq, pageNum, subject, name, pass, content)
		String pageNum = request.getParameter("pageNum");
		
		BoardDTO dto = new BoardDTO();
		dto.setBno(Integer.parseInt(request.getParameter("bno")));
		dto.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		dto.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		dto.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		
		dto.setSubject(request.getParameter("subject"));
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		dto.setContent(request.getParameter("content"));
		
		dto.setIp(request.getRemoteAddr()); // ip 주소 추가
		
		//답글쓰기 메서드
		//BoardDAO 객체
		BoardDAO dao = new BoardDAO();
		
		dao.reInsert(dto);
		
		
		//페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?pageNum="+pageNum);
		forward.setRedirect(true); // 주소가 바뀌어야하니까
		
		
		
		return forward;
	}
	
}
