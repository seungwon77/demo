<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>sign up</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<%--    <script src="/js/main.js"></script>--%>
    <script>
        function search() {
            let memberNo = document.getElementById("memberNo").value;
            location.href='/member/search?memberNo=' + memberNo;
        }

        function registerForm() {
            location.href="/member/register";
        }

        function register(formName) {
            let form = document.forms.namedItem(formName);
            const data = new FormData(form);
            const value = Object.fromEntries(data.entries());
            $.ajax({
                type: "POST" ,
                async: true ,
                url: "/api/v1/member",
                dataType : "json",
                timeout: 3000,
                data: JSON.stringify(value),
                contentType: "application/json",
                error: function (request, status, error) {
                    console.log(error);
                }, success: function (response, status, request) {
                    console.log(response, status);
                }, beforeSend: function () {

                }, complete: function () {

                }
            });
        }
    </script>
</head>
<body>
<h1>Sign Up</h1>
<hr>

<form name="registerForm">
    id : <input type="text", name="memberId"> <br>
    password: <input type="password" name="password"> <br>
    email : <input type="text" name="email" placeholder="abc@abc.com"> <br>
    cell : <input type="text" name="cellNo" placeholder="01012341234">
    <button onclick="register('registerForm')">회원가입</button>
</form>
</body>
</html>