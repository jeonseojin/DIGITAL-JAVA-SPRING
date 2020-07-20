<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form method="post" action="<%=request.getContextPath()%>/signup">
    <div class="container-body">
        <div class="container-id">
            <div class="text-id">아이디</div>
            <div class="box-id">
                <input type="text" name="id" id="id">
                <label for="id">@naver.com</label></div>
        </div>

        <div class="container-pw">
            <div class="text-pw">비밀번호</div>
            <div class="box-pw">
                <input type="password" name="pw" id="pw">
                <a href="#"></a>
            </div>
        </div>

        <div class="container-pw">
            <div class="text-pw">비밀번호 확인</div>
            <div class="box-pw">
                <input type="password" name="pw2" id="pw2">
                <a href="#"></a>
            </div>
        </div>
        
        
        <div class="container-gender">
            <div class="text-gender">성별</div>
            <div class="box-gender">
                <select name="gender" id="gender">
                    <option value="">성별</option>
                    <option value="male">남자</option>
                    <option value="female">여자</option>
                </select>
            </div>
        </div>
        
        <div class="container-email">
            <div class="text-bold">본인 확인 이메일</div>
            <div class="box-email">
                <input type="text" name="email" id="email">
            </div>
        </div>
        <button class="btn-submit btn btn-success">가입하기</button>
    </div>
</form>