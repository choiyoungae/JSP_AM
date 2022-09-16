<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Map<String, Object> articleRow = (Map<String, Object>)request.getAttribute("articleRow");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정하기</title>
</head>
<body>

	<h1>게시물 수정하기</h1>
	
	<h2><%=(int)articleRow.get("id") %>번 게시물</h2>
	
	<form action="doModify" method="post">
	
		<input type="hidden" name="id" value="${param.id}" />
	
		<div>
			<p>번호 : <%=(int)articleRow.get("id") %></p>
			<p>날짜 : <%=(LocalDateTime)articleRow.get("regDate") %></p>
			<p>제목 : 
				<input autocomplete="off" placeholder="제목을 입력해주세요" name="title" type="text"
				value="<%=articleRow.get("title") %>">
			</p>
			<p>내용 : 
				<textarea autocomplete="off" placeholder="내용을 입력해주세요" name="body"><%=articleRow.get("body") %></textarea>
			</p>
		</div>
		
		<button type="submit">수정하기</button>
	</form>	
	
	<div><a href="list">리스트로 돌아가기</a></div>
</body>
</html>