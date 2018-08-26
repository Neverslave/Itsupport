package controller;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import interceptor.LoginInterceptor;
import mdoel.User;


import java.util.ArrayList;
import java.util.List;

import static mdoel.User.userDao;


public class AdminController extends Controller {
    @Before(LoginInterceptor.class)
    public void index(){
        if(getAttr("flag")=="N"){
            redirect("/login");
            return;
        }else {

            if (getSessionAttr("user") != null) {
                renderTemplate("index.html");
            } else {
                String user = getPara("username");
                String password = getPara("password");
                if (userDao.findById(user) == null) {
                    renderJson("msg", "1");

                } else {
                    String pwd = userDao.findById(user).getStr("password");//数据库中取出的密码;
                    if (pwd.equals(password)) {
                        setSessionAttr("user", user);
                        renderJson("msg", "2");
                    } else {
                        renderJson("msg", "3");
                    }
                }
            }
        }
    }


    public void users(){
        List<User> users = new ArrayList<User>();
        users = userDao.find("select * from users");
        System.out.println(users);

    }



}
