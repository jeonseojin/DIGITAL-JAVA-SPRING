<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test="${user == null}">
	<head>
		<title>Home</title>
	</head>	
	<form action="<%=request.getContextPath()%>/" method="POST" class="form-wi">
			<h1>
				로 그 인 
			</h1>
			<input type="text" class="form-control" name="id" placeholder="아이디">
			<input type="password" class="form-control" name="pw" placeholder="비밀번호">
			<br>
			<button type="submit" class="btn btn-success">Login</button>
			
	</form>
	<input type="hidden" value="${isLogin}" id="isLogin">
	<input type="hidden" value="${id}" id="id">
</c:if>
<script type="text/javascript">
	$(function(){
		var id = $('#id').val();
		var isLogin = $('#isLogin').val()
		if(isLogin == 'false' && id != '')
			alert(id+'가 없가나 비밀번호가 잘못 되었습니다.')
	})
</script>