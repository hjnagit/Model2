package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("M : BoardUpdateAction.execute 호출");
		
		//1. 전달된 정보 저장!! 무조건 해야한다 -> 항상 해야한다
		//bno, pageNum
		//?로 보냈으니까 파라메터로 전달된것이다
		// 디비에서 사용해야하면 형식에 맞게 바꿔줘야한다
		// 디비에서 사용안하면 그냥 String으로 받는다
		int num = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		
		//BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		
		//2. 전달된 글번호를 바탕으로 해당 글정보 모두를 가져오기
		//본문 읽을때 사용했던 메서드를 사용한다
		BoardDTO dto = dao.getBoard(num);
		
		System.out.println("M : 수정할 데이터(기존데이터)" + dto);
		
		
		//3. DB에서 가져온 정보를 request 영역에 저장
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		
		
		//4. 페이지 이동(view- ./board/updateForm.jsp)
		ActionForward forward = new ActionForward();
		forward.setPath("./board/updateForm.jsp");
		forward.setRedirect(false);
		
		
		return forward;
	}

	
}
