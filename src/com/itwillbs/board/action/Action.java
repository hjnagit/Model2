package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	//변수x 상수o
	//인스턴스메서드x 추상메서드o
	// => 상속을 통해서 추상메서드를 오버라이딩해서 사용(강제성)
	
	// ActionForward는 이동정보를 저장하는 객체다
	//execute를 실행하면  request와 response정보를 넣어주면 ActionForward가 생성된다
	
	// /**하면 생긴다 document주석
	
	
	/**
	 * 추상메서드이며, 반드시 오버라이딩 해서 사용해야함
	 * 실행할 때 request, response 정보를 전달해야지만 호출가능
	 * 호출이 완료되면 ActionForward(주소, 방식) 정보를 리턴
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
	//Exception에 커서놓고 f3번두르면 attach source를 눌러서 파일 찾아가는 곳에서
	// jdk에 가면 src.zip을 찾아서 열어서 추가하면 코드를 볼 수 있다
	
	
	
	
}
