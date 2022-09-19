<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Map<String, Object>> memberRows = (List<Map<String, Object>>)request.getAttribute("memberRows");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>

	<script>
		// 비밀번호 확인하기
		const loginPw = document.getElementsByClassName("loginPw");
		const loginPwConfirm = document.getElementsByClassName("loginPwConfirm");
		
		function confirmPw() {
			if(loginPw.loginPw.value == loginPwConfirm.loginPwConfirm.value) {
				return true;
			}
			
			alert("비밀번호를 확인하세요.");
			return false;
		}
	</script>

	<h1>회원가입</h1>

	<form action="doJoin" method="post" onsubmit="return confirmPw()">
		<div>아이디 : <input autocomplete="off" placeholder="아이디를 입력해주세요" name="loginId" type="text"></div>
		<div>비밀번호 : <input autocomplete="off" placeholder="비밀번호를 입력해주세요" name="loginPw" class="loginPw"></div>
		<div>비밀번호 확인 : <input autocomplete="off" placeholder="비밀번호를 다시 입력해주세요" name="loginPwConfirm" class="loginPwConfirm"></div>
		<div>이름 : <input autocomplete="off" placeholder="이름을 입력해주세요" name="name"></div>
		
		<button type="submit">가입하기</button>
	</form>	
	
	<div><a href="list">리스트로 돌아가기</a></div>
</body>
</html>