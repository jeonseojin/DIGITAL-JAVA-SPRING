<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <c:if test="${board.isDel == 'N'.charAt(0)}">
<form class="board-bar" method="POST" action="<%=request.getContextPath()%>/board/modify">	
		<div class="input-group mb-3">
			<div class="input-group mb-3 input-group-sm">
				<div class="input-group-prepend">
					<input class="input-group-text" name="num" value="${board.num}">
				</div>
				<input type="text" class="form-control" name="title" value="${board.title}">
			</div>
		</div>	 
	<table class="table table-hover">
      <tr class="table-height">
      	<th><input class="input-group-text input-text text-small" value="작성자:" readonly></th>
        <th><input class="input-group-text input-text text-small" name="writer"value="${board.writer}" readonly></th>
        <th><input class="input-group-text input-text" value="작 성 일  :" readonly></th>
        <th><input class="input-group-text input-text" name="registerDate" value="${board.registerDate}" readonly></th>
        <th><input class="input-group-text input-text text-small" value="조회수:" readonly></th>
        <th><input class="input-group-text input-text text-small" name="views" value="${board.views}" readonly></th>
      </tr>
	</table>
    <div class="form-group">
		<label for="comment">게시물 내용</label>
		<textarea class="form-control" rows="5" name="content">${board.content}</textarea>
	</div>

  		<a href="<%=request.getContextPath() %>/board/list" class="btn-board left"><button type="button" class="btn btn-outline-secondary">목록</button></a>
		<button class="btn btn-outline-info btn-board mama">수정하기</button>
</form>
</c:if>