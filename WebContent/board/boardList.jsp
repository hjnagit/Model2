<%@page import="com.itwillbs.board.db.BoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">

h2{
	color: red;
}



</style>
</head>
<body>
<h1>boardList.jsp</h1>

<h2>게시판 목록</h2>

<h1 style="color: white; background-color: red; display: inline;">데브가현 헤이터</h1>
<h2>돈미새 가현님을 헤이트하는 모임</h2>


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
	
	<c:forEach var="dto" items="${requestScope.boardList }">
		<tr>
			<td>${dto.bno }</td>
			<td>
			<c:if test="${dto.re_lev > 0 }">
				<img src="./board/level.gif" width="${dto.re_lev *20 }" height="10">
				<img src="./board/re.gif">
			</c:if>
			<a href="./BoardContent.bo?bno=${dto.bno }&pageNum=${requestScope.pageNum}">${dto.subject }</a>
			</td>
			
			<td>${dto.name }</td>
			<td>${dto.readcount }</td>
			<td>${dto.date }</td>
			<td>${dto.ip }</td>
		<tr>
	
	</c:forEach>
	
		
</table>


<!-- 하단 페이징 처리 -->
<c:if test="${cnt != 0 }">
	<!-- 이전 : 직전 페이지블럭의 첫번째 페이지 호출  -->
	<c:if test="${startPage > pageBlock }">
		<a href="./BoardList.bo?pageNum=${startPage-pageBlock}">[이전]</a>
	</c:if>
	
	<!-- 1,2,3,4,... -->
	<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
		<a href="./BoardList.bo?pageNum=${i }">[${i }]</a>
	</c:forEach>
	
	<!-- 다음 -->
	<c:if test="${endPage < pageCount }">
		<a href="./BoardList.bo?pageNum=${startPage+pageBlock}">[다음]</a>
	</c:if>

</c:if>







</body>
</html>