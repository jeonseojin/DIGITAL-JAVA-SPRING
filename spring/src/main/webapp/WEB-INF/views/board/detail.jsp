<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   <form>
		<div class="form-group">
		  <label>게시글 번호</label>
		  <input type="text" class="form-control" id="num" value="${board.num}" readonly name="num">
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
		  <label>추천수</label>
		  <input type="text" class="form-control" name="like" value="${board.like}" readonly>
		  <button type="button" class="btn btn-outline-success col-12" id="like">추천</button>
		</div>
		<div class="form-group">
		  <div class="form-group">
			  <label for="comment">게시글:</label>
			  <textarea class="form-control" rows="5" name="content" readonly>${board.content}</textarea>
		  </div>
		</div>
		<c:if test="${board.file != null}">
			<div>
				<a href="<%=request.getContextPath()%>/board/download?fileName=${board.file}" class="form-control">${board.oriFile}</a>
			</div>
		</c:if>
   </form>
	<a href="<%=request.getContextPath()%>/board/list?&page=${cri.page}& type=${cri.type}&search=${cri.search}"><button>목록</button></a>
	<c:if test="${user !=null }">
   		<a href="<%=request.getContextPath()%>/board/register"><button>글쓰기</button></a>
	</c:if>
	<c:if test="${user!=null && board.writer == user.id}">
	   <a href="<%=request.getContextPath()%>/board/modify?num=${board.num}"><button>수정</button></a>
	   <!-- modify에서 수정을 하려면 수정하려는 게시글 번호를 알아야 하기 때문에 ?num=${board.num}을 입력해야한다. (삭제도 동일) -->
	   <a href="<%=request.getContextPath()%>/board/delete?num=${board.num}"><button>삭제</button></a>
	</c:if>
	
   <script>
	$(function(){
		$('#like').click(function(){
			var num = $('input[name=num]').val();
			$.ajax({
			async:true,
			type:'POST',
			data:num,
			url:"<%=request.getContextPath()%>/board/like2",
			dataType:"json",
			contentType:"application/json; charset=UTF-8",
			success : function(data){//서버에서 데이터를 보내 줄 경우 name이 like인 값을 가져와서 먼저 보여줌
				if(!data['isUser']){
					alert("로그인한 회원만 추천할 수 있습니다.")
				}else{
					if(data['like']==-1){
							alert('추천은 1번만 가능합니다.')
						}else{
							 $('input[name=like]').val(data['like'])//같은 시간대에 보는 사용자가 추천을 눌렀을 상황을 대비하여 먼저 DB에서 정보를 불러옴
						}
					}    
			    }
			});
		})
	})
   </script>
     

