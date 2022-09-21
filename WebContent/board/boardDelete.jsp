<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>boardDelete.jsp</h1>

<%

//bno=87&pageNum=1 이거는 파라메타로 전달되는 것
//${param.bno } 이렇게 파람으로 부른다
int bno = Integer.parseInt(request.getParameter("bno"));
String pageNum = request.getParameter("pageNum");

//el표현식으로 사용하면 null일때 심가한 오류가 생기지 않음
// 공백으로 찍어준다

//디비에 저장되는 것은 폼태그안에 저장
//디비에 저장안하면 주소줄로 저장해서 전달한다

//bno는 디비에 저장하니까 폼에 저장해서 넘기고
//pageNum은 디비에 저장안하니까 주소줄을 통해서 넘긴다

//그니까 디비에 넣을거 아니면 히든으로 넘기지 마라
%>

bno : ${param.bno }
pageNum : ${param.pageNum }




<fieldset>
<legend>게시판 삭제하기</legend>

<form action="./BoardDeleteAction.bo?pageNum=${param.pageNum }" method="post">
<input type="hidden" name="bno" value="${param.bno }">
비밀번호 : <input type="password" name="pass">
<input type="submit" value="삭제하기">
</form>

</fieldset>



</body>
</html>