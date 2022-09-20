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
<title>회원가입</title>
</head>
<body>

	<script>
		var JoinForm__submitDone = false;
		
		function JoinForm__submit(form) {
			if (JoinForm__submitDone){
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
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
			if (form.loginPwConfirm.value.length == 0) {
				alert('비밀번호 확인을 입력해주세요');
				form.loginPwConfirm.focus();
				return;
			}
			form.name.value = form.name.value.trim();
			if (form.name.value.length == 0) {
				alert('이름을 입력해주세요');
				form.name.focus();
				return;
			}
			// 비밀번호 확인
			if (form.loginPw.value != form.loginPwConfirm.value) {
				alert('비밀번호가 일치하지 않습니다');
				form.loginPw.focus();
				return;
			}
			form.submit();
			JoinForm__submitDone = true;
		}
	</script>

	<h1>회원가입</h1>

	<form action="doJoin" method="post" onsubmit="JoinForm__submit(this); return false;">
		<div>아이디 : <input autocomplete="off" placeholder="아이디를 입력해주세요" name="loginId" type="text"></div>
		<div>비밀번호 : <input autocomplete="off" placeholder="비밀번호를 입력해주세요" name="loginPw" type="password"></div>
		<div>비밀번호 확인 : <input autocomplete="off" placeholder="비밀번호를 다시 입력해주세요" name="loginPwConfirm" type="password"></div>
		<div>이름 : <input autocomplete="off" placeholder="이름을 입력해주세요" name="name" type="text"></div>
		
		<button type="submit">가입하기</button>
	</form>	
	
	<div><a href="list">리스트로 돌아가기</a></div>
</body>
</html>