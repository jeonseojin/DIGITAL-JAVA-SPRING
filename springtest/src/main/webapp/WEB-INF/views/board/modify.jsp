<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <c:if test="${board.isDel == 'N'.charAt(0)}">
<form class="board-bar" method="POST" action="<%=request.getContextPath()%>/board/modify" enctype="multipart/form-data">	

	<div class="input-group mb-3">
			   <div class="input-group-prepend">
			   <span class="input-group-text">${board.num}</span>
				</div>		 
				<input type="text" class="form-control" name="title" value="${board.title}" >
				<div class="input-group-prepend">
				    <span class="input-group-text">${board.writer}</span>
				    <span class="input-group-text">${board.registerDate}</span>
				    <span class="input-group-text">${board.views}</span>
				    <span class="float-right btn-like input-group-text"><i class="far fa-thumbs-up"></i></span>
				    <span class="input-group-text">${board.up}</span>
				</div>
			</div>
    <div class="form-group">
		<label for="comment">내용</label>
		<textarea class="form-control" rows="5" name="content">${board.content}</textarea>
	</div>
	<c:if test="${board.file != null }">
		<div class="board-file detail form-group">
			<span class="fine-name">${board.oriFile}</span>
			<span class="btn-file-del"><i class="fas fa-times"></i></span>
			<input type="hidden" name="file" value="${board.file}">
		</div>
	</c:if>
	<div class="board-add-file detail form-group">
		<input type="file" name="file2">
	</div>
  		<a href="<%=request.getContextPath() %>/board/list" class="btn-board left"><button type="button" class="btn btn-outline-secondary">목록</button></a>
		<button class="btn btn-outline-info btn-board mama">수정하기</button>
		<input type="hidden" name="num" value="${board.num}" readonly>
		<input type="hidden" name="writer" value="${board.writer}" readonly>
		<input type="hidden" name="registerDate" value="${board.registerDate}" readonly>
		<input type="hidden" name="views" value="${board.views}" readonly>
</form>
</c:if>
<script>
	$(function(){
		$('.btn-file-del').click(function(){
			$('.board-file').empty();//자식을 지우기 위해서 삭제
		})
		$('input[name=file2]').change(function(){
			if($('input[name=file]').val() =='' ||//들어온값이 공백일경우
					$('input[name=file]').val() == null ||//들어온값이 없을 경우
					typeof($('input[name=file]').val()) == 'undefined')//type이 undefined가 될 경우
					return;
			$(this).val('');
			alert('첨부파일은 하나만 추가 가능합니다. 기존 첨부파일을 삭제하세요.')
		})
	})
</script>
