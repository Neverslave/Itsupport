package common;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import model.*;
import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import router.AdminRouter;
import router.FrontRouter;



public class MyConfig extends JFinalConfig{


    /**
     *
     * @param me
     */
    @Override
    public void configConstant(Constants me){
        PropKit.use("config.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));

    }

    @Override
    public void configRoute(Routes me) {
        me.add(new AdminRouter());
        me.add(new FrontRouter());


    }

    @Override
    public void configEngine(Engine me) {
        //配置模板引擎

    }

    @Override
    public void configPlugin(Plugins me) {
        //配置外部引擎 数据库等
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        me.add(dp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.setBaseSqlTemplatePath(PathKit.getWebRootPath());//设置sql模板位置
        //arp.addSqlTemplate("/sql/sql.sql");
        arp.addMapping("user","user", User.class);
        arp.addMapping("session", Session.class);
        arp.addMapping("login_log","username", LoginLog.class);
       // arp.addMapping("user", Blog.class);//表与model相关联 数据库映射需要在加入plugin前

        me.add(arp);
        me.add(new EhCachePlugin());
        me.add(new Cron4jPlugin());

    }

    @Override
    public void configInterceptor(Interceptors me) {
        //配置全局！！拦截器每一个请求都拦截
    }

    @Override
    public void configHandler(Handlers me) {
        //接管所有的web请求，可以进行扩展
    }
}