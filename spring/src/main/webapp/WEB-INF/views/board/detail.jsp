<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   <form>
		<div class="form-group">
		  <label>게시글 번호</label>
		  <input type="text" class="form-control" id="num" value="${board.num}" readonly>
		</div>
		<div class="form-group">
		  <label>게시글 제목</label>
		  <input type="text" class="form-control" name="title" value="${board.title}" readonly>
		</div>
		<div class="form-group">
		  <label>작성자</label>
		  <input type="text" class="form-control" name="writer" value="${board.writer}" readonly>
		</div>
		<div class="form-group">
		  <label>작성일</label>
		  <input type="text" class="form-control" name="refisterDate" value="${board.registerDate}" readonly>
		</div>
		<div class="form-group">
		  <label>조회수</label>
		  <input type="text" class="form-control" name="views" value="${board.views}" readonly>
		</div>
		<div class="form-group">
		  <div class="form-group">
			  <label for="comment">게시글:</label>
			  <textarea class="form-control" rows="5" name="content" readonly>${board.content}</textarea>
		  </div>
		</div>
   </form>
   <a href="<%=request.getContextPath()%>/board/register"><button>글쓰기</button></a>
   <a href="<%=request.getContextPath()%>/board/modify?num=${board.num}"><button>수정</button></a>
   <!-- modify에서 수정을 하려면 수정하려는 게시글 번호를 알아야 하기 때문에 ?num=${board.num}을 입력해야한다. (삭제도 동일) -->
   <a href="<%=request.getContextPath()%>/board/delete?num=${board.num}"><button>삭제</button></a>

   
     

