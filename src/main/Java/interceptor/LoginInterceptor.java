package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import mdoel.UserAttribute;

public class LoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String session = controller.getSessionAttr("user");
        //UserAttribute userAttribute=(UserAttribute)controller.getSession().getAttribute("userAttribute");
        if (controller.getPara()!=null){
            System.out.println(controller.getPara());
            controller.setAttr("flag", "Y");
            inv.invoke();
        }
        else {
            if (session == null) {
                //若用户为空则重定向到登录页面
                //增加一个flag 判断登录状态
                controller.setAttr("flag", "N");
                inv.invoke();
            } else {
                //若有用户直接进入控制台
                //todo 通过记忆session 在这里更新session
                controller.setAttr("flag", "Y");
                inv.invoke();
            }
        }

    }
}
