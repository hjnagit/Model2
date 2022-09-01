package com.itwillbs.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : BoardListAction.execute 호출");
		
		//디비처리
		//BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		// 게시판에 작성되어 있는 전체 글 개수 - 알아야 한다
		//count를 통해서 개수를 구한다
		// 디비에 갔다 와야한다
		int cnt = dao.getBoardCount();
		
		
		// 페이징 처리-------------------------------------------------------------
		
		// 한 페이지에 보여줄 글의 개수 설정
		//./BoardList.bo?pageNum=5&pageSize=3
		String urlPageSize = request.getParameter("pageSize");
		if(urlPageSize == null){
			urlPageSize = "2";
		}
		
		int pageSize = Integer.parseInt(urlPageSize);
		
		// 한 페이지가 몇 번째 페이지인지 계산
		// => 페이지 정보가 없을 경우 항상 1페이지
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null){
			pageNum = "1";
		}
		
		// 페이지 시작행 번호 계산 1 11 21 31 .....
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage -1) * pageSize +1; // 번호 계산하는 로직
		
		// 끝행 번호 계산 10 20 30 40....
		int endRow = currentPage * pageSize;
		
		// 디비에서 가져와야한다
		// 디비에서 데이터를 가져올 때 나눠서 가져오는 것이 성능이 더 좋다 - 한꺼번에 들고오는 것 보다
		
		// 페이징 처리-------------------------------------------------------------
		
		
		
		
		//dao 메서드 중에서 게시판 글정보를 모두 가져오는 메서드 호출
		//List<BoardDTO> boardList = dao.getBoardList();
		List<BoardDTO> boardList = dao.getBoardList(startRow, pageSize); // 이것을 호출
		
		System.out.println("M : 게시판 글정보 저장완료!");
		
		
		
		// 페이징 처리2(하단 페이지 링크)-------------------------------------------------------------
		
		// 전체 페이지 수 계산
		//ex) 전체 글 50개 -> 한페이지 10개씩 출력, 5개 페이지 필요
		//ex) 전체 글 55개 -> 한페이지 10개씩 출력, 6개 페이지 필요
		int pageCount = cnt/pageSize + (cnt%pageSize == 0 ? 0 : 1);
		
		// 한 화면에 보여줄 페이지 수(페이지 블럭)
		int pageBlock = 3;
		
		//페이지 블럭 시작 번호 1-10 -> 1 11-20->2...
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
		
		//페이지 블럭 끝 번호 1-10->10 11-20 -> 20 /...
		int endPage = startPage + pageBlock -1;
		
		// 총 페이지, 페이지 블럭끝번호 비교
		if(endPage > pageCount){
			endPage = pageCount;
		}
		
		
		// 페이징 처리2(하단 페이지 링크)-------------------------------------------------------------
		
		
		
		//view 페이지 정보 전달을 위해서 request 영역에 저장
		request.setAttribute("boardList", boardList);
		//session에 담아도 동작은 하는데 -> 이 데이터가 세션에 유지될 필요가 없으니까
		// 게시판 정보는 필요할 때만 있으면 되니까
		// 항상 유지되어야하는 것은 세션에 -> 로그인시 아이디정보, 장바구니
		
		System.out.println("M : 리스트 정보 저장 request 영역");
		
		// 페이징 커리 정보 전달(request 영역)
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("cnt", cnt);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		System.out.println("M : 페이징 커리 정보 저장");
		
		// 화면에 출력
		// 페이지 이동(화면전환)
		ActionForward forward = new ActionForward();
		forward.setPath("./board/boardList.jsp");
		forward.setRedirect(false); // 화면만 바뀌는 false
		
		
		return forward;
	}

}
