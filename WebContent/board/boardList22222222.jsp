<%@page import="com.itwillbs.board.db.BoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인페이지</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <script src="https://kit.fontawesome.com/daddbc6c0e.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="./css/boarderList.css">
<style type="text/css">
 
table {
	border-collapse: collapse;
	border-spacing: 0;
  }
  section.notice {
	padding: 80px 0;
  }
  
  .page-title {
	margin-bottom: 60px;
  }
  .page-title h3 {
	font-size: 28px;
	color: #333333;
	font-weight: 400;
	text-align: center;
  }
  .button12{float: right; margin-right: 15%; margin-bottom: 20px;}

  .board-table {
	font-size: 13px;
	width: 100%;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
  }
  
  .board-table a {
	color: #333;
	display: inline-block;
	line-height: 1.4;
	word-break: break-all;
	vertical-align: middle;
  }
  .board-table a:hover {
	text-decoration: underline;
  }
  .board-table th {
	text-align: center;
  }
  
  .board-table .th-num {
	width: 100px;
	text-align: center;
  }
  
  .board-table .th-date {
	width: 200px;
  }
  
  .board-table th, .board-table td {
	padding: 14px 0;
  }
  
  .board-table tbody td {
	border-top: 1px solid #e7e7e7;
	text-align: center;
  }
  
  .board-table tbody th {
	padding-left: 15px;
	padding-right: 14px;
	border-top: 1px solid #e7e7e7;
	text-align: center;
  }
  
  .board-table tbody th p{
	display: none;
  }
  
  .btn {
	display: inline-block;
	padding: 0 30px;
	font-size: 15px;
	font-weight: 400;
	background: transparent;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	border: 1px solid transparent;
	text-transform: uppercase;
	-webkit-border-radius: 0;
	-moz-border-radius: 0;
	border-radius: 0;
	-webkit-transition: all 0.3s;
	-moz-transition: all 0.3s;
	-ms-transition: all 0.3s;
	-o-transition: all 0.3s;
	transition: all 0.3s;
  }
  
  .btn-dark {
	background: #555;
	color: #fff;
  }
  
  .btn-dark:hover, .btn-dark:focus {
	background: #373737;
	border-color: #373737;
	color: #fff;
  }
  
  .btn-dark {
	background: #555;
	color: #fff;
  }
  
  .btn-dark:hover, .btn-dark:focus {
	background: #373737;
	border-color: #373737;
	color: #fff;
  }
  
  /* reset */
  
  * {
	list-style: none;
	text-decoration: none;
	padding: 0;
	margin: 0;
	box-sizing: border-box;
  }
  .clearfix:after {
	content: '';
	display: block;
	clear: both;
  }
  .container {
	width: 1100px;
	margin: 0 auto;
  }
  .blind {
	position: absolute;
	overflow: hidden;
	clip: rect(0 0 0 0);
	margin: -1px;
	width: 1px;
	height: 1px;
  }

.pageN{
	text-align: center;
}

</style>
</head>
<body>
<section class="notice">
  <div class="page-title">
        <div class="container">
            <h3>Q&A</h3>
        </div>
    </div>
   <div class="button12">
    <input type="button" class="btn" value="글쓰기" onclick="location.href='./BoardWrite.bo'">
   </div>
<!-- <h3><a href="./BoardWrite.bo">글쓰기</a></h3>
<h3><a href="./FileBoardWrite.bo">파일첨부 글쓰기</a></h3> -->


<!-- board list area -->
    <div id="board-list">
        <div class="container">
            <table class="board-table">
                <thead>
                <tr>
                    <th scope="col" class="th-title">제목</th>
                    <th scope="col" class="th-date">작성자</th>
                    <th scope="col" class="th-date">등록일</th>
                    <th scope="col" class="th-num">조회수</th>
                </tr>
                </thead>
			<c:forEach var="dto" items="${requestScope.boardList }">
                <tbody>
                <tr>
                    <th>
                    <c:if test="${dto.re_lev > 0 }">
						<img src="./board/level.gif" width="${dto.re_lev *20 }" height="10">
						<img src="./board/re.gif">
					</c:if>
                    <a href="./BoardContent.bo?bno=${dto.bno }&pageNum=${requestScope.pageNum}">${dto.subject }</a>
                    </th>
                    
                    <th>${dto.name }</th>
                    <td>${dto.date }</td>
                    <td>${dto.readcount }</td>
                </tr>
                
                
                </tbody>
            </c:forEach>
            </table>
        </div>
    </div>

</section>



<%-- <table border="1">
	
	
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
	
		
</table> --%>


<!-- 하단 페이징 처리 -->
<div class="pageN">
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
</div>

<script>
    window.onscroll = function() {myFunction()};
    
    var navbar = document.getElementById("navbar");
    var sticky = navbar.offsetTop;
    
    function myFunction() {
      if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
      } else {
        navbar.classList.remove("sticky");
      }
    }
</script> 





</body>
</html>