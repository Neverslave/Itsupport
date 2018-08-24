package controller;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import interceptor.LoginInterceptor;


import static mdoel.User.userDao;


public class AdminController extends Controller {
    @Before(LoginInterceptor.class)
    public void index(){
        if(getSessionAttr("user")!=null){
            renderTemplate("index.html");
        }
        else {
        String user =getPara("username");
        String password = getPara("password");
        if(userDao.findById(user)==null){
          renderJson("msg","1");

            System.out.println("1");
        }else {
            String pwd = userDao.findById(user).getStr("password");//数据库中取出的密码;
            if (pwd.equals(password)) {
                setSessionAttr("user",user);
                renderJson("msg","2");
            } else {
                renderJson("msg", "1");
            }
            }
        }
    }
    //登录验证
    public void login(){

        //setSessionAttr("userAttribute", )
        renderTemplate("login.html");

    }


}
