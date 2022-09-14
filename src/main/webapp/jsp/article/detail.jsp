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
<title>게시물 상세보기</title>
</head>
<body>
	
	<h1><%=(int)articleRow.get("id") %>번 게시물</h1>
	
	<div>
		<p>번호 : <%=(int)articleRow.get("id") %></p>
		<p>날짜 : <%=(LocalDateTime)articleRow.get("regDate") %></p>
		<p>제목 : <%=articleRow.get("title") %></p>
		<p>내용 : <%=articleRow.get("body") %></p>
	</div>
	
	<div><a href="list">리스트로 돌아가기</a></div>
</body>
</html>