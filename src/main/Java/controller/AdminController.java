package controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import interceptor.LoginInterceptor;
import mdoel.UserAttribute;

public class AdminController extends Controller {
    @Before(LoginInterceptor.class)
    public void index(){
        renderTemplate("index.html");
    }
    //登录验证
    public void login(){

        //setSessionAttr("userAttribute", )
         renderTemplate("login.html");

    }


}
