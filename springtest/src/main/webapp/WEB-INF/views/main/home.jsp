<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<div>
			<h1>
				로 그 인 
			</h1>
			<form action="<%=request.getContextPath()%>/" method="POST">
				
			<input type="text" class="form-control" placeholder="아이디">
			<input type="password" class="form-control" name="pw" placeholder="비밀번호">
			<br>
			<button type="button" class="btn btn-success">Login</button>
			<input type="hidden" value="${isLogin}" id="isLogin">
			<input type="hidden" value="${id}" id="id">
		</div>
	</form>
			<script type="text/javascript">
				$(function(){
					var id = $('#id').val();
					var isLogin = $('#isLogin').val()
					if(isLogin =='false' && id !='')
						alert(id +'가 없거나 비밀번호가 잘못 되었습니다.')
				})
			</script>
</body>
</html>
