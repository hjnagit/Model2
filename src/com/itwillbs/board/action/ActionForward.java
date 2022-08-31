package com.itwillbs.board.action;

public class ActionForward {
	//페이지 이동을 위한 정보를 저장하는 객체
	
	private String path; //이동할 주소
	private boolean isRedirect; // 이동할 방식
	
	// 방식은 2가지이다 - 우리들의 약속...
	// true - sendRedirect() 방식으로 이동
	// false - forward() 방식으로 이동
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() { // is가 붙어있는 변수를 get이 is 로 나온다
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}



}
