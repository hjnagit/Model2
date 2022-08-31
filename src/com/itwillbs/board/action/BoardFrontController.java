package com.itwillbs.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BoardFrontController extends HttpServlet{
	
	//doget dopost 하나로 합치기
	
	//url -> http://localhost:8088/Model2/board.bo
	//uri ->/Model2/board.bo
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET,POST방식 호출 - doProcess() 실행");
		//doProcess가 하는 일
	
		
		System.out.println("\n 1. 가상주소 계산 - 시작");
		//1. 가상주소 계산-------------------------------------------------------------------------
		String requestURI = request.getRequestURI();
		System.out.println("C : requestURI : " + requestURI); // 프로젝트명과 뒤주소 모두 가지는 주소
		
		String ctxPath = request.getContextPath();
		System.out.println("C : ctxPath : " + ctxPath); // 프로젝트명
		
		String command = requestURI.substring(ctxPath.length()); // ctxPath의 길이만큼 잘르겠다
		System.out.println("C : command : " + command);
		//1. 가상주소 계산-------------------------------------------------------------------------
		System.out.println("1. 가상주소 계산 - 끝");
		
		
		
		
		System.out.println("\n 2. 가상주소 매핑 - 시작");
		//2. 가상주소 매핑-------------------------------------------------------------------------
		Action action = null; // 업캐스팅으로 코드바꾸기
		ActionForward forward = null; // 이동티켓 - 미리 변수만 들어두기 여러번 쓸거라서
		
		// 1번 디비를 사용하지 않을 경우 이 패턴으로 사용
		if(command.equals("/BoardWrite.bo")){ // 1단계 마지막에 만들진 주소가 이것과 똑같나
			// 글쓰기 페이지 보여주기 - 주소가 맞으면
			// 여기서 판단해야함 디비를 쓸건지 말건지 => DB경로가 필요없음
			// 그럼 뷰로 간다
			System.out.println("C : /BoardWrite.bo 호출");
			System.out.println("C : DB정보가 필요없음 -> view페이지로 이동");
			
			forward = new ActionForward(); // 객체 생성하기, 정보저장하기
			forward.setPath("./board/writeForm.jsp"); //주소 저장
			forward.setRedirect(false); // 이동방식을 false forward 방식
			// 아직 이동한 것은 아니고 저장만 한 것 
		}
		
		// 2번 디비를 사용할 경우 이러한 패턴으로 사용
		else if(command.equals("/BoardWriteAction.bo")){ // 가상주소가 동일하고 디비작업이 필요할 때
			System.out.println("C : /BoardWriteAction.bo 호출");
			System.out.println("C : DB작업 o, 페이지 이동");
			
			//BoardWriteAction() 객체 생성
			//BoardWriteAction bwAction = new BoardWriteAction();
			action = new BoardWriteAction();
			
			try {
//				forward = bwAction.execute(request, response); // forward를 리턴한 것을 받는다
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//2단계 끝이고 3단계로가서 forward의 정보고 이동한다
		}
		
		else if(command.equals("/BoardList.bo")){ // 게시판 리스트로 이동하기
			System.out.println("C : /BoardList.bo 호출");
			System.out.println("C : DB정보가 필요, 페이지 이동x, 페이지 출력o");
			
			//BoardListAction() 객체 생성
//			BoardListAction listAction = new BoardListAction();
			action = new BoardListAction();
			try {
				System.out.println("C : 해당 Model 객체 호출");
//				forward = listAction.execute(request, response);
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		
		//2. 가상주소 매핑-------------------------------------------------------------------------
		System.out.println("2. 가상주소 매핑 - 끝");
		
		
		
		System.out.println("\n 3. 가상주소 이동 - 시작");
		//3. 가상주소 이동-------------------------------------------------------------------------
		if(forward != null){
			//페이지 이동정보가 있을 때
			
			if(forward.isRedirect()){
				//true - sendRedirect() 방식으로 이동
				System.out.println("C : true-" + forward.getPath()+"이동, sendRedirect방식");
				
				response.sendRedirect(forward.getPath());
				
			}else{
				//false - forward() 방식으로 이동
				System.out.println("C : false-" + forward.getPath()+"이동, forward방식");
				
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
				
			}
		}
		//3. 가상주소 이동-------------------------------------------------------------------------
		System.out.println(" 3. 가상주소 이동 - 끝");
		
	}
	/////////////////////////////////////doProcess///////////////////////////////////////////////////////////
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET방식 호출 - doGet() 실행");
		//doGet이 실행되면 doProcess를 호출함
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST방식 호출 - doPost() 실행");
		//doPost이 실행되면 doProcess를 호출함
		doProcess(request, response);
	}
	
}
