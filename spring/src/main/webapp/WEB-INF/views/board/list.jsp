<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 게시판 -->
	  <h2>게시판</h2>
	  <p>자유롭게 글을 입력하세요.</p>            
	  <table class="table">
	    <thead>
	      <tr>
	         <th>번호</th>
		        <th>제목</th>
		        <th>작성자</th>
		        <th>작성일</th>
		        <th>조회수</th>
	      </tr>
	    </thead>
	    <tbody>
	    <!-- c: -->
	    	<c:forEach var="board" items="${list}">
			      <tr>
			        <td>${board.num}</td>
			        <td><a href="<%=request.getContextPath()%>/board/detail?num=${board.num}&page=${pm.criteria.page}& type=${pm.criteria.type}&search=${pm.criteria.search}">${board.title}</a></td>
			        <td>${board.writer}</td>
			        <td>${board.registerDate}</td>
			        <td>${board.views}</td>
			      </tr>
			</c:forEach>
	      
	    </tbody>
	  </table>
	  
<!-- 페이지네이션 -->	  
	   <ul class="pagination justify-content-center">
	    	<c:if test="${pm.prev}"><!-- 11페이지로 이동하는 버튼 -->
				<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/board/list?page=${pm.startPage-1} & type=${pm.criteria.type}&search=${pm.criteria.search}"><i class="fas fa-angle-double-left"></i></a></li>
			</c:if>
		    <c:if test="${pm.criteria.page != 1}"><!-- 한칸씩 뒤로 이동하는 버튼 -->
				<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/board/list?page=${pm.criteria.page-1} & type=${pm.criteria.type}&search=${pm.criteria.search}"><i class="fas fa-chevron-left"></i></a></li>
			</c:if>
			<c:forEach var="index" begin="${pm.startPage}" end="${pm.endPage}">
				<li class="page-item <c:if test="${index == pm.criteria.page}">active</c:if>"><a class="page-link"" href="<%=request.getContextPath()%>/board/list?page=${index} & type=${pm.criteria.type}&search=${pm.criteria.search}">${index}</a></li>
			</c:forEach>
			<c:if test="${pm.criteria.page != pm.lastEndPage}"><!-- 한칸씩 앞으로 이동하는 버튼 -->
				<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/board/list?page=${pm.criteria.page+1} & type=${pm.criteria.type}&search=${pm.criteria.search}"><i class="fas fa-chevron-right"></i></a></li>
			</c:if>
			<c:if test="${pm.next}"><!-- 10페이지로 이동하는 버튼 -->
				<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/board/list?page=${pm.endPage+1} & type=${pm.criteria.type}&search=${pm.criteria.search}"><i class="fas fa-angle-double-right"></i></a></li>
			</c:if>
		</ul>
		
<!-- 검색(기본적으로 GET방식으로 전송) -->
	<form action="<%=request.getContextPath()%>/board/list">
		<div class="input-group mb-3">
				<select class="form-control" name="type"id="sel1">
				<c:if test="${pm.criteria.type == 0}">selected</c:if>
					<option value="0" <c:if test="${pm.criteria.type == 0}">selected</c:if>>전체</option>
					<option value="1" <c:if test="${pm.criteria.type == 1}">selected</c:if>>작성자</option>
					<option value="2" <c:if test="${pm.criteria.type == 2}">selected</c:if>>제목</option>
					<option value="3" <c:if test="${pm.criteria.type == 3}">selected</c:if>>내용</option>
				</select>
				<input type="text" class="form-control" placeholder="Search" name="search" value="${pm.criteria.search}">
				<div class="input-group-append">
					<button class="btn btn-success" type="submit">검색</button>
				</div>
		</div>
	</form>