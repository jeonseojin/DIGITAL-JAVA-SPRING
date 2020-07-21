<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form class="board-bar">
	<c:if test="${board eq null }">
		<h1>해당 게시물은 없는 게시물입니다.</h1>
	</c:if>
	<c:if test="${board ne null}">
		<!-- isDel이 char이기 때문에 문자열로 변경 -->
		<c:if test="${board.isDel == 'Y'.charAt(0) }">
			<h1>해당 게시물은 삭제된 게시물 입니다.</h1>
		</c:if>
		<c:if test="${board.isDel == 'N'.charAt(0) }">
			<div class="input-group mb-3">
			   <div class="input-group-prepend">
			   <span class="input-group-text">${board.num}</span>
			</div>		 
			<input type="text" class="form-control" value="${board.title}" readonly>
			<div class="input-group-prepend">
			    <span class="input-group-text">${board.writer}</span>
			    <span class="input-group-text">${board.registerDate}</span>
			    <span class="input-group-text">${board.views}</span>
			</div>
			</div>
			 
			<div class="form-group">
				<label for="comment">게시물 내용</label>
				<textarea class="form-control" rows="5" name="content">${board.content}</textarea>
			</div>
			<div>
				<a href="<%=request.getContextPath() %>/board/list?page=${cri.page}&type=${cri.type}&search=${cri.search}" class="btn-board left"><button type="button" class="btn btn-outline-secondary">목록</button></a>
				<c:if test="${user!=null}">
					<a href="<%=request.getContextPath() %>/board/register" class="btn-board"><button type="button" class="btn btn-outline-success">등록</button></a>
					<c:if test="${user.id == board.writer}">
						<a href="<%=request.getContextPath() %>/board/modify?num=${board.num}" class="btn-board mama"><button type="button" class="btn btn-outline-info">수정</button></a>
						<a href="<%=request.getContextPath() %>/board/delete?num=${board.num}" class="btn-board mama"><button type="button" class="btn btn-outline-danger">삭제</button></a>
					</c:if>
				</c:if>
			</div>
		</c:if>
	</c:if>
</form>