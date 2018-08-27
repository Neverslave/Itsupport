package controller;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import common.Kit.IPkit;
import interceptor.LoginInterceptor;
import mdoel.User;
import service.LoginService;


import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;



public class AdminController extends Controller {
    private User userDao = new User().dao();
    LoginService srv = LoginService.me;
    public void index() {
    keepPara("returnUrl");
    render("login.html"); //跳转登录页面


    }

    /**
     * 登录
     */
    public void doLogin(){
        Boolean keepLogin = getParaToBoolean("keepLogin",false);
        String loginIp = IPkit.getRealIp(getRequest());
        Ret ret = srv.login(getPara("username"),getPara("password"),keepLogin,loginIp);
        if(ret.isOk()){
            String sessionId = ret.getStr(LoginService.sessionIdName);
            int maxAgeInSeconds = ret.getInt("maxAgeInSeconds");
            setCookie(LoginService.sessionIdName,sessionId,maxAgeInSeconds,true);//放置cookie
            setAttr(LoginService.loginAccountCacheName,ret.get(LoginService.loginAccountCacheName));
            ret.set("returnUrl",getPara("returnUrl","/admin"));
        }
        renderJson(ret);
    }


    public void users() {
        List<User> users;
        users = userDao.find("select * from users");
        System.out.println(users);

    }




    }

