<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:if test="${user == null}">
<head>
	<title>Home</title>
</head>
	<form action="<%=request.getContextPath()%>/" method="POST">
		<div>
			<h1>
				로 그 인 
			</h1>
			<input type="text" class="form-control" name="id" placeholder="아이디">
			<input type="password" class="form-control" name="pw" placeholder="비밀번호">
			<button type="submit" class="btn btn-success">로 그 인</button>
			<input type="hidden" value="${isLogin}" id="isLogin">
			<input type="hidden" value="${id}" id="id">
		</div>
	</form>
</c:if>
<button id="ajax">버튼</button>
<script>
	$(function(){
		$('#ajax').click(function(){
			$.ajax({
				async:true,
				type:'POST',
				data:JSON.stringify({"id":"123","num":"456"}),
				url:"<%=request.getContextPath()%>/test2",
				dataType:"json",
				contentType:"application/json; charset=UTF-8",
				success : function(data){
					console.log(data['res']);
				}
			});
		})
	})
</script>
