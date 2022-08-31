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
	
	

%>

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




</body>
</html>