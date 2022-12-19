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