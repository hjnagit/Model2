<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>reWriteForm.jsp</h1>

<h2> 답글 페이지 </h2>

<!-- bno/pageNum/re_ref/re_lev/re_seq -->

bno : ${param.bno }
pageNum : ${param.pageNum }
re_ref : ${param.re_ref }
re_lev : ${param.re_lev }
re_seq : ${param.re_seq }

<fieldset>
	<form action="./BoardReWriteAction.bo?pageNum=${param.pageNum }" method="post">
	<input type="hidden" name="bno" value="${param.bno }">
	<input type="hidden" name="re_ref" value="${param.re_ref }">
	<input type="hidden" name="re_lev" value="${param.re_lev }">
	<input type="hidden" name="re_seq" value="${param.re_seq }">
	
		글쓴이 : <input type="text" name="name"><br>
		비밀번호 : <input type="password" name="pass"><br>
		제목 : <input type="text" name="subject" value="[답글]"><br>
		내용 : <textarea rows="10" cols="20" name="content"></textarea><br>
		
		<input type="submit" value="글쓰기">
	</form>
</fieldset>



</body>
</html>