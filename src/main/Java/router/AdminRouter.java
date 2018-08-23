package router;

import com.jfinal.config.Routes;
import controller.AdminController;

public class AdminRouter extends Routes {
    @Override
    public void config() {

        add("/admin", AdminController.class,"admin");

    }
}
