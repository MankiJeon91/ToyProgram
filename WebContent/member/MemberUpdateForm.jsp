<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="spms.vo.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보</title>
</head>
<body>
<jsp:useBean id="member" scope="request" class="spms.vo.Member" />
<h1>회원정보</h1>
<form action="update" method="post">
	번호 : <input type="text" name="no" value="<%=member.getNo() %>" readonly="readonly"><br>
	이름 : *<input type="text" name="name" value="<%=member.getName()%>"><br>
	이메일 : <input type="text" name="email" value="<%=member.getEmail() %>"><br>
	가입일 : <%=member.getCreatedDate() %><br>
	<input type="submit" value="저장">
	<input type="button" value="삭제" onclick='location.href="delete?no=<%= member.getNo()%>"'>
	<input type="reset" value="취소" onclick='location.href="location.href=list"'>
</form>
</body>
</html>