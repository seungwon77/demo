<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko" >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>회원조회</title>
    <link href="/css/main.css" rel="stylesheet"/>
    <script src="/js/main.js" defer></script>
</head>
<body>
<div class="table__wrap">
    <table class="table">
        <thead class="table__header">
        <tr class="table__row">
            <th class="table__cell u-text-left">MemberNo</th>
            <th class="table__cell u-text-right">MemberId</th>
            <th class="table__cell u-text-right">Email</th>
            <th class="table__cell u-text-right">CellNo</th>
        </tr>
        </thead>

        <tbody>
        <tr class="table__row">
            <td class="table__account table__cell"></td>
            <td class="table__account table__cell"></td>
            <td class="table__account table__cell"></td>
            <td class="table__account table__cell"></td>
        </tr>
        </tbody>
        <tfoot>

        <tr class="table__row table__row--last">
            <td colspan="2" class="table__balance table__cell">회원을 조회 하시려면 회원번호를 입력하세요.</td>
            <td colspan="2" class="table__limit table__cell u-text-right u-font-mono">
                <input type="text" name="memberNo" placeholder="회원번호" id="memberNo">&nbsp;
                <btn class="btn" href="#" onclick="search();">조회</btn>
                <btn class="btn" href="#" onclick="registerForm();">신규등록</btn>
            </td>
        </tr>

        </tfoot>
    </table>
</div>
</body>
</html>