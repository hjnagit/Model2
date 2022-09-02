package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardContentAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("M : BoardContentAction.execute() 호출");
		
		//전달된 정보 저장 bno, pageNum
		//주소줄에 넘어오는 정보 저장
		//디비에 저장하면 형변환을 하고 -> 디비의 원래 값을 가져다 써야하니까
		//디비에 저장을 안 하면 형변환을 하지 않음
		
		// => 전달되는 파라메타값의 경우 
		//-> 테이블에 저장되는 값이면 형변환o
		//-> 테이블에 저장안되는 값이면 형변환x
		
		int bno = Integer.parseInt(request.getParameter("bno")); // 숫자
		String pageNum = request.getParameter("pageNum"); // String
		
		//BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		// 조회수 1 증가하기 - 메서드 updateReadcount()
		dao.updateReadcount(bno);
		
		System.out.println("M : 조회수 1증가 완료!!!");
		
		//게시판 글 1개의 정보를 가져와서 출력
		BoardDTO dto = dao.getBoard(bno);
		
		//Model 객체에서는 정보 출력xxx
		//-> 정보출력은 view에서 한다
		// -> Model 객체에 있는 정보를 view로 이동
		
		// request 영역에 저장하기
		request.setAttribute("dto", dto);
		
		//request.setAttribute("dto", dao.getBoard(bno));
		//이렇게 한번에 넘기는 것도 가능 
		
		// 페이지넘버도 저장한다
		// 정보를 출력할 때 사용하는 것
		request.setAttribute("pageNum", pageNum);
		
		
		//출력하는 뷰페이지로 이동
		//이동하면 무조건 불러야하는 객체다
		ActionForward forward = new ActionForward();
		forward.setPath("./board/boardContent.jsp");
		forward.setRedirect(false); 
		// 영역에 데이터를 저장하면 무조건 false를 사용한다
		//request에 저장한다 -> false
		// 주소가 jsp다 -> false-> .jsp라는 주소는 공개되지 않아야 하므로
		
		
		return forward;
	}
}
