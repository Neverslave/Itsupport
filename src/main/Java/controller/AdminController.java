package controller;
import com.jfinal.core.Controller;
import com.jfinal.json.Json;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Ret;
import Kit.IPkit;
import service.LoginService;
import model.*;
import service.UserService;

import java.util.List;



public class AdminController extends Controller {
    private User userDao = new User().dao();
    LoginService srv = LoginService.me;
    public void index() {
        if(getCookie(LoginService.sessionIdName)==null){
            redirect("/login");
        }else {
            //keepPara("returnUrl");
            render("index.html");

        }
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


    public void getUsers() {
        renderJson("rows:"+new UserService().getUsers());

    }
        public void users(){
        render("/admin/users.html");
        }



    }

