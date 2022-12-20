<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>Sign Up</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <style type="text/css">
        .errormsg {
            color: red;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 align="center" class="text-primary">회원등록</h2>
    <hr />
    <div> </div>

    <form:form action="/member/register" method="POST" modelAttribute="memberV1">
        <div class="form-group">
            <label>ID :</label><form:input path="memberId" size="20" cssClass="form-control" placeholder="ID" />
            <small><form:errors path="memberId" cssClass="errormsg" /></small>
        </div>
        <div class="form-group">
            <label>Password:</label><form:password path="password" size="20" cssClass="form-control" placeholder="비밀번호" />
            <small><form:errors path="password" cssClass="errormsg" /></small>
        </div>
        <div class="form-group">
            <label>Email:</label><form:input path="email" size="30" cssClass="form-control" placeholder="이메일" />
            <small><form:errors path="email" cssClass="errormsg" /></small>
        </div>
        <div class="form-group">
            <label>Cell No:</label><form:input path="cellNo" size="13" cssClass="form-control" placeholder="010-1111-2222" />
            <small><form:errors path="cellNo" cssClass="errormsg" /></small>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">등록</button>
        </div>
    </form:form>
</div>
</body>
</html>