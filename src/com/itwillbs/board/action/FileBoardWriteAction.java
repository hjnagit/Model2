package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileBoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("M : FileBoardWriteAction_execute 호출");
		
		//첨부파일 처리(업로드)
		// upload
		// 파일의 실제 위치(서버의 어디에 저장되는가?)
		String realPath = request.getRealPath("/upload");
		System.out.println("realPath : " + realPath); // 실제 서버에 저장되는 주소
		
		//업로드 파일의 크기지정
		int maxSize = 10*1024*1024; //10MB
		
		//파일 업로드 -> MultipartRequest 객체 생성
		MultipartRequest multi = 
				new MultipartRequest(
						request,
						realPath,
						maxSize,
						"UTF-8",
						new DefaultFileRenamePolicy()
						);
		
		System.out.println("M : 파일업로드 성공!");
		
		//한글처리 - 멀티파트에서 해줘서 안해도 됨
		//전달된 파라메타 저장
		//dto 객체 생성
		BoardDTO dto = new BoardDTO();
		
		//form태그의 속성이 변경되었기 때문에
		dto.setName(multi.getParameter("name"));
		dto.setPass(multi.getParameter("pass"));
		dto.setSubject(multi.getParameter("subject"));
		dto.setContent(multi.getParameter("content"));
		
		//file은 파라메타로 받아올 수 없다
		//enc로 안 받아오면 파라메타로 받을 수 있다
		//dto.setFile(multi.getFilesystemName("file")); - 서버에 올라가는 파일 이름
		//dto.setFile(multi.getOriginalFileName("file")); - 파일 고유의 이름
		dto.setFile(multi.getFilesystemName("file"));
		
		dto.setIp(request.getRemoteAddr()); // 이거는 이렇게
		
		//DB에 첨부파일 글쓰기
		//실제 파일은 서버에 저장, 파일의 이름만 db에 저장
		BoardDAO dao = new BoardDAO();
		dao.boardWrite(dto);
		
		//페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
	}

}
