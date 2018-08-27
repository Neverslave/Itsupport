package controller;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import interceptor.LoginInterceptor;
import mdoel.User;


import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

import static mdoel.User.userDao;


public class AdminController extends Controller {
    //@Before(LoginInterceptor.class)
    public void index() {
       String users =  getCookie("users");
       if(users==null){
           redirect("/login");
       }else{
           render("/admin/index.html");
       }


    }


    public void users() {
        List<User> users;
        users = userDao.find("select * from users");
        System.out.println(users);

    }

    public void loginValidate() {
        String user = getPara("username");
        String password = getPara("password");
        String pwd = userDao.findById(user).get("password");
        if (password == pwd) {
            setCookie("users", user, 3600);
            renderJson("msg", "1");

        } else {
            renderJson("msg", "2");
        }


    }
}
