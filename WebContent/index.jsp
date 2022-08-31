<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- <h1>index.jsp</h1> -->

<%
	//MVC 프로젝트 실생 시작 페이지
	//MVC 프로젝트에서는 index.jsp 페이지 외 절대로 .jsp페이지 실행금지
	// => 주소창에 .jsp 주소가 보이면 MVC 패턴이 깨짐
	
//	response.sendRedirect("./Test.bo");
	
	//글쓰기 페이지로 이동
	response.sendRedirect("./BoardWrite.bo");
	
	//글 리스트 페이지 이동
//	response.sendRedirect("./boardList.bo");

%>










</body>
</html>