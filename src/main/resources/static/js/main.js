function search() {
    let memberNo = document.getElementById("memberNo").value;
    location.href='/member/search?memberNo=' + memberNo;
}

function registerForm() {
    location.href="/member/register";
}

function register() {
    let data = {};
    data.memberId = $("#memberId").val();
    data.password = $("#password").val();
    data.email = $("#email").val();
    data.cellNo = $("#cellNo").val();
    $.ajax({
        type: "POST" ,
        async: true ,
        url: "/api/v1/member",
        dataType : "json",
        timeout: 3000,
        data: JSON.stringify(data),
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