<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>${title}</div>
<form action="<%=request.getContextPath()%>/test" method="GET">
<!-- 중요한 정보(아이디와 비밀번호와 같은 경우) method를 POST 타입으로 해서 보내야한다.  -->
	<input type="text" name="id" placeholder="아이디">
	<br>
	<input type="password" name="pw" placeholder="비밀번호">
	<button type="submit">전송</button>
</form>
