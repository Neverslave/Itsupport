package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import mdoel.UserAttribute;

public class LoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        inv.invoke();

    }
}
