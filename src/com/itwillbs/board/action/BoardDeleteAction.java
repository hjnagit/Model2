package com.itwillbs.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("M : BoardDeleteAction_execute 호출");

		// 전달된 데이터 저장 - pass bno pageNum
		// 직전 페이지에서 무슨 정보 가져왔는지 확이하고 정보 받는다
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pass = request.getParameter("pass");
		String pageNum = request.getParameter("pageNum");

		// dto에 담기
		BoardDTO dto = new BoardDTO();

		dto.setBno(bno);
		dto.setPass(pass);

		// BoardDAO 객체
		BoardDAO dao = new BoardDAO();

		// 게시글 삭제
		int result = dao.deleteBoard(dto);

		// result => -1 0 1 정보

		// java에서
		// js호출하기-------------------------------------------------------------------
		// 응답 페이지는 html형태로 만들겠다는 뜻!
		response.setContentType("text/html; charset=UTF-8");
		// 응답정보로 출력 객체 생성(통로 생성)
		PrintWriter out = response.getWriter();

		if (result == 0) {
			out.println("<script>");
			out.println("alert('비밀번호 오류');");
			out.println("history.back();");
			out.println("</script>");
			// 페이지 이동 완료된 상태이다
			// 자원해제하기 - 연결 통로끊어주기
			out.close();
			return null; // 3단계로 이동 안하게 foward정보를 주지않고 null로 준다
			// 즉 컨트롤러를 통한 이동을 안하겠다
			// 그냥 자바스크립트로만 이동하겠다는 뜻
		} else if (result == -1) {
			out.println("<script>");
			out.println("alert('게시글 없음');");
			out.println("history.back();");
			out.println("</script>");
			// 페이지 이동 완료
			out.close();
			return null;
		} else {
			// result == 1
			out.println("<script>");
			out.println("alert('게시글 삭제 완료');");
			out.println("location.href = './BoardList.bo?pageNum=" + pageNum + "';");
			out.println("</script>");

			out.close();

			return null;
		}

	}

}
