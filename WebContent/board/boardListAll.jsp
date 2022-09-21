<%@page import="com.itwillbs.board.db.BoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>boardListAll.jsp</h1>

<h2>게시판 목록</h2>


<%
	//request.setAttribute("boardList", boardList); 의 정보를 출력
	
	List<BoardDTO> boardList = (List<BoardDTO>)request.getAttribute("boardList");
	
//	request.setAttribute("pageNum", pageNum);
//	request.setAttribute("cnt", cnt);
//	request.setAttribute("pageCount", pageCount);
//	request.setAttribute("pageBlock", pageBlock);
//	request.setAttribute("startPage", startPage);
//	request.setAttribute("endPage", endPage);
	
	String pageNum = (String) request.getAttribute("pageNum");
	int cnt = (int) request.getAttribute("cnt");
	int pageCount = (int) request.getAttribute("pageCount");
	int pageBlock = (int) request.getAttribute("pageBlock");
	int startPage = (int) request.getAttribute("startPage");
	int endPage = (int) request.getAttribute("endPage");
	
	
	

%>

<h3><a href="./BoardWrite.bo">글쓰기</a></h3>

<table border="1">
	<tr>
		<td>번호</td>
		<td>제목</td>
		<td>글쓴이</td>
		<td>조회수</td>
		<td>작성일</td>
		<td>IP</td>
	<tr>
	<%for(int i=0; i<boardList.size(); i++){ 
		//DB -> DTO -> List 이렇게 담았으니까 반대로 꺼낸다
		BoardDTO dto = boardList.get(i);
	%>
	<tr>
		<td><%=dto.getBno() %></td>
		<td><%=dto.getSubject() %></td>
		<td><%=dto.getName() %></td>
		<td><%=dto.getReadcount() %></td>
		<td><%=dto.getDate() %></td>
		<td><%=dto.getIp() %></td>
	<tr>
	<%} %>
	
</table>


<%

//하단 페이징 처리
if(cnt != 0){
	//이전
	if(startPage > pageBlock){
		//pageBlock 3으로 설정함 -> 페이지가 4페이지로 가면 이전 생성
		%>
			<a href="./BoardList.bo?pageNum=<%=startPage-pageBlock%>">[이전]</a>
		<%
		//5페이지를 보고있으면 startPage는 4  pageBlock3 -> 1페이지로 간다
		// 12페이지를 보고있으면 시작10 블럭3 -> 7페이지
		// 이전을 누르면 직전 페이지의 startPage로 간다
		// 이전 => 직전페이지의 첫페이지로 간다
	}
	
	//1,2,3,4,
	for(int i=startPage; i<= endPage; i++){
		%>
			<a href="./BoardList.bo?pageNum=<%=i%>">[<%=i %>]</a>
		<%
	}
	
	
	//다음
	
	//1페이지를 보고있으면 endPage는 3
	// 다음을 누르면 다음 페이지의 처음으로 간다
	// 3페이지 에서 다음을 누르면 다음 블럭의 첫 페이지인 4페이지로 간다
	if(endPage < pageCount){
		%>
			<a href="./BoardList.bo?pageNum=<%=startPage+pageBlock%>">[다음]</a>		
		<%
	}
	
	
	
}


%>






</body>
</html>