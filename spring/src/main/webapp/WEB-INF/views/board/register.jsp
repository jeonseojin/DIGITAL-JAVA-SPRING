<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- 등록버튼을 클릭할 때 내용을 한꺼번에 서버에 등록하기 위해서는 form태그로 묶어야 한번에 전송가능 -->
    <!-- Multiple inputs -->
	<form action="<%=request.getContextPath()%>/board/register"  method="POST" enctype="multipart/form-data">
		<div class="input-group mb-3">
			<div class="input-group-prepend">
			<span class="input-group-text">게시글 제목</span>
		</div>
			<input type="text" class="form-control" name="title" placeholder="제목을 입력하세요.">
		</div>
		<div class="form-group">
		  <label>작성자</label>
		  <input type="text" class="form-control" name="writer" value="${user.id}" readonly>
		</div>
   
	    <div class="form-group">
		  <label>내용:</label>
		  <textarea class="form-control" rows="5" name="content"></textarea>
		  
		</div>
	    
		<!-- 파일 업로드창 추가 -->
	    <div class="form-group">
		        <label>파일</label>
		        <input type="file" class="form-control-file border"name="file2">
		</div>
		<button>저장</button>
	</form>
	
	