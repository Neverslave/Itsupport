login = function() {
    var username = $("#inputEmail").val();
    var password = $("#inputPassword").val();
    var keepLogin = $("#remember-password").val();
    if(username===""){
        alert("请输入用户名");
        return false;
    }
    if(password===""){
        alert("请输入密码");
        return false;
    }
    //ajax 去服务端校验
    var data ={username:username,password:password,keepLogin:keepLogin};
    $.ajax({
        type: "POST",
        url: "/admin/doLogin",
        data: data,
        dataType: 'json',
        success: function (ret) {
            // 业务层验证成功
            if (ret.state == "ok") {
                location.href = ret.returnUrl;
                return;
            } else {
                alert(ret.msg);
            }
        }

    })
}