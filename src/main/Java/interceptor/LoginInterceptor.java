package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import mdoel.UserAttribute;

public class LoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {

        inv.invoke();
        Controller controller = inv.getController();
        String session = controller.getSessionAttr("user");
      //UserAttribute userAttribute=(UserAttribute)controller.getSession().getAttribute("userAttribute");
      if(session == null){
          //若用户为空则重定向到登录页面
          controller.redirect("/admin/login");
      }
      else {
          //若有用户直接进入控制台
          //todo 通过记忆session 在这里更新session
      }
    }
}
