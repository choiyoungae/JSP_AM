<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

	<script>
		var LoginForm__submitDone = false;
		
		function LoginForm__submit(form) {
			if (LoginForm__submitDone){
				alert('처리중 입니다');
				return;
			}
			
			form.loginId.value = form.loginId.value.trim();
			if (form.loginId.value.length == 0) {
				alert('아이디를 입력해주세요');
				form.loginId.focus();
				return;
			}
			form.loginPw.value = form.loginPw.value.trim();
			if (form.loginPw.value.length == 0) {
				alert('비밀번호를 입력해주세요');
				form.loginPw.focus();
				return;
			}
			
			form.submit();
			JoinForm__submitDone = true;
		}
	</script>

	<h1>회원가입</h1>

	<form action="doLogin" method="post" onsubmit="LoginForm__submit(this); return false;">
		<div>아이디 : <input autocomplete="off" placeholder="아이디를 입력해주세요" name="loginId" type="text"></div>
		<div>비밀번호 : <input autocomplete="off" placeholder="비밀번호를 입력해주세요" name="loginPw" type="password"></div>
		
		<button type="submit">로그인</button>
	</form>	
	
	<div><a href="../home/main">취소</a></div>
</body>
</html>