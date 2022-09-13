package com.itwillbs.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardUpdateProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("M : BoardUpdateProAction.execute() 호출");
		//한글처리
		request.setCharacterEncoding("UTF-8");
		
		//1. 전달된 정보 저장(수정할 데이터 - bno, name, subject, content)/ pageNum)
		String pageNum = request.getParameter("pageNum");
		
		BoardDTO dto = new BoardDTO();
		dto.setBno(Integer.parseInt(request.getParameter("bno")));
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		
		//정보를 저장-dto /// 정보를 dao
		//2. 수정할 데이터를 디비로 보내서 정보 수정
		BoardDAO dao = new BoardDAO();
		
		int result = dao.updateBoard(dto);
		
		
		//result => -1 0 1 정보
		
		//java에서 js호출하기-------------------------------------------------------------------
		//응답 페이지는 html형태로 만들겠다는 뜻!
		response.setContentType("text/html; charset=UTF-8");
		//응답정보로 출력 객체 생성(통로 생성)
		PrintWriter out = response.getWriter();
		
		if(result == 0){
			out.println("<script>");
			out.println("alert('비밀번호 오류');");
			out.println("history.back();");
			out.println("</script>");
			//페이지 이동 완료된 상태이다
			//자원해제하기 - 연결 통로끊어주기
			out.close();
			return null; //3단계로 이동 안하게 foward정보를 주지않고 null로 준다
			//즉 컨트롤러를 통한 이동을 안하겠다
			//그냥 자바스크립트로만 이동하겠다는 뜻
		}
		else if(result == -1){
			out.println("<script>");
			out.println("alert('게시글 없음');");
			out.println("history.back();");
			out.println("</script>");
			//페이지 이동 완료
			out.close();
			return null;
		}
		
		//result == 1
		
		out.println("<script>");
		out.println("alert('정보 수정 완료');");
		out.println("location.href = './BoardList.bo?pageNum="+ pageNum +"';");
		out.println("</script>");
		
		out.close();
		
		return null;
	}
	
}
