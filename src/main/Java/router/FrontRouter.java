package router;

import com.jfinal.config.Routes;
import controller.IndexController;

public class FrontRouter extends Routes{
    @Override
    public void config() {
        add("/", IndexController.class);
    }
}

