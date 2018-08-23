login = function() {
    var username = $("#inputEmail").val();
    var password = $("#inputPassword").val();
    if(username===""){
        alert("请输入用户名");
        return false;
    }
    if(password===""){
        alert("请输入密码");
        return false;
    }
    //ajax 去服务端校验
    var data ={username:username,password:password};
    $.ajax({
            type:"POST",
            url:"/admin",
            data:data,
            dataType:'json',
              success:function(data){
                 if(data.msg==="2"){
                       location.href="/admin";
                }else{
                     alert("登录失败，请重试!");
                 }
                 }

        });

}