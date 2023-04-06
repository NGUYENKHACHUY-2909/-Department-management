$(function(){
    var isRememberMe = storage.getRememberMe();
    $("#input-check").attr("checked", isRememberMe);
})
function login(){
    var userName = $("#username").val();
    var password = $("#password").val();
    if(!userName){
        showErrorMessageLogin("Please input username!!!");
        return;
    }
    // if(!password){
    //     showErrorMessageLogin("Please input password!!!");
    //     return;
    // }
    // if(userName.length < 5 || userName.length >50 || password.length < 5){
    //     showErrorMessageLogin("Login Fail!!!");
    //     return;
    // }
    $.ajax({
        type: 'Get',
        url: 'http://localhost:8080/api/login',
        dataType: 'json',
        // dataType: 'json'
        contentType: 'application/json; charset=utf-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Basic " + btoa(userName + ":" + password));
        },
        success: function(data, textStatus, xhr){
            var isRememberMe =document.getElementById("input-check").checked;
            storage.saveRememberMe(isRememberMe);
            window.location.replace("http://127.0.0.1:5500/index.html");
            storage.setItem("username", userName);
            storage.setItem("fullName", data.fullName);
            storage.setItem("id", data.id);
            storage.setItem("password", password);
        },
        error(jqXhR, textStatus, errorThrow){
            if(jqXhR.status == 401){
                showErrorMessageLogin("Login Fail!");
                return;
            }
            alert("Error System!!!");
            // return;
            // console.log(textStatus);
        }
        
    });
}
function showErrorMessageLogin(message){
    $("#error-message").html(message);
}
