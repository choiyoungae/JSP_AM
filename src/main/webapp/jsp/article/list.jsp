<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.KoreaIT.java.am.dto.Article" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Article> articles = (List<Article>)request.getAttribute("articles");
List<Map<String, Object>> memberRows = (List<Map<String, Object>>)request.getAttribute("memberRows");
int totalPage = (int)request.getAttribute("totalPage");
int currentPage = (int)request.getAttribute("page");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>
	
	<div class="wrap">
		
		<%@ include file="../part/topBar.jspf" %>
	
		<h1>게시물 리스트</h1>
		
		<div><a href="write">글쓰기</a></div>
		
		<table border="1" bordercolor="navy">
			<colgroup>
				<col width="50"/>
				<col width="250"/>
				<col width="150"/>
				<col width="200"/>
			</colgroup>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>날짜</th>
				<th>삭제</th>
				<th>수정</th>
			</tr>
		
			<% for(Article article : articles) { %>
			<tr>
				<td><%=article.id %></td>
				<td><a href="detail?id=<%=article.id %>"><%=article.title %></a></td>
				<td><%=article.extra__writer %></td>
				<td><%=article.regDate %></td>
				<td><a href="delete?id=<%=article.id %>">삭제하기</a></td>
				<td><a href="modify?id=<%=article.id %>">수정하기</a></td>
			</tr>
			<% } %>
		</table>
		
		<div class="page">
			<% if(currentPage > 1) { %>
			<a href="list?page=1">◀◀</a>
			<% } %>
			<% 
			int pageSize = 5;
			int from = currentPage - pageSize;
			if(from < 1) {
				from = 1;
			}
			int end = currentPage + pageSize;
			if(end > totalPage) {
				end = totalPage;
			}
			for(int i=from; i<=end; i++) { %>
			<a class="<%= currentPage == i ? "current" : ""%>" href="list?page=<%=i %>"><%=i %></a>
			<% } %>
			<% if(currentPage < totalPage) { %>
			<a href="list?page=<%= totalPage %>">▶▶</a>
			<% } %>
		</div>
	</div>
	
	<style type="text/css">
		.wrap {
			text-align : center;
		}
		table {
			position : relative;
			left : 50%;
			transform : translateX(-50%);
			margin : 20px 0;
		}
		.page a.current {
			color : red;
			font-weight : bold;
		}
	</style>
</body>
</html>