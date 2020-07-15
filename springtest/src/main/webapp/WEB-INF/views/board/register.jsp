<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="board-bar" method="POST" action="<%=request.getContextPath()%>/board/register">
		<div class="input-group mb-3">		 
			<input type="text" class="form-control" placeholder="제목을 입력하세요" name="title" >
			<input type="text" class="form-control" placeholder="아이디를 입력하세요" name="writer" id="input-mo" >
		</div>
		<div class="form-group">
			<label for="comment">게시물 내용</label>
			<textarea class="form-control" rows="5" name="content"></textarea>
		</div>
		<a href="<%=request.getContextPath() %>/board/register" class="btn-board"><button type="submit" class="btn btn-outline-success">저장</button></a>
</form>
<a href="<%=request.getContextPath() %>/board/list"><button type="button" class="btn btn-outline-secondary">목록</button></a>