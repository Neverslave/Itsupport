package service;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import model.*;

import com.jfinal.kit.Ret;

import java.util.Date;

/**
 * 登录业务
 */
public class LoginService {

    public static final LoginService me = new LoginService();
    private User userDao = new User().dao();


    // 存放登录用户的 cacheName
    public static final String loginAccountCacheName = "user";

    // "jfinalId" 仅用于 cookie 名称，其它地方如 cache 中全部用的 "sessionId" 来做 key
    public static final String sessionIdName = "jfinalId";

    /**
     * 登录成功返回 sessionId 与 loginAccount，否则返回一个 msg
     */
    public Ret login(String userName, String password, boolean keepLogin, String loginIp) {
        userName = userName.toLowerCase().trim();
        password = password.trim();
        User user = userDao.findFirst("select * from user where username=?", userName);
        if (user == null) {
            return Ret.fail("msg", "用户名或密码不正确");
        }


        // 未通过密码验证
        if (user.getPassword().equals(password)==false) {
            return Ret.fail("msg", "用户名或密码不正确");
        }

        // 如果用户勾选保持登录，暂定过期时间为 3 年，否则为 120 分钟，单位为秒
        long liveSeconds =  keepLogin ? 3 * 365 * 24 * 60 * 60 : 120 * 60;
        // 传递给控制层的 cookie
        int maxAgeInSeconds = (int)(keepLogin ? liveSeconds : -1);
        // expireAt 用于设置 session 的过期时间点，需要转换成毫秒
        long expireAt = System.currentTimeMillis() + (liveSeconds * 1000);
        // 保存登录 session 到数据库
        Session session = new Session();
        String sessionId = StrKit.getRandomUUID();
        session.setId(sessionId);
        session.setUsername(user.getUsername());
        session.setExpireAt(expireAt);
        if ( ! session.save()) {
            return Ret.fail("msg", "保存 session 到数据库失败，请联系管理员");
        }

        user.put("sessionId", sessionId);                          // 保存一份 sessionId 到 loginAccount 备用
        CacheKit.put(loginAccountCacheName, sessionId, user);
        createLoginLog(user.getUsername(), loginIp);

        return Ret.ok(sessionIdName, sessionId)
                .set(loginAccountCacheName, user)
                .set("maxAgeInSeconds", maxAgeInSeconds);   // 用于设置 cookie 的最大存活时间
    }

    public User getLoginAccountWithSessionId(String sessionId) {
        return CacheKit.get(loginAccountCacheName, sessionId);
    }

    /**
     * 通过 sessionId 获取登录用户信息
     * sessoin表结构：session(id, accountId, expireAt)
     *
     * 1：先从缓存里面取，如果取到则返回该值，如果没取到则从数据库里面取
     * 2：在数据库里面取，如果取到了，则检测是否已过期，如果过期则清除记录，
     *     如果没过期则先放缓存一份，然后再返回
     */
    public User loginWithSessionId(String sessionId, String loginIp) {
        Session session = Session.dao.findById(sessionId);
        if (session == null) {      // session 不存在
            return null;
        }
        if (session.isExpired()) {  // session 已过期
            session.delete();		// 被动式删除过期数据，此外还需要定时线程来主动清除过期数据
            return null;
        }

        User loginAccount = userDao.findById(session.getUsername());
        // 找到 loginAccount 并且 是正常状态 才允许登录
        if (loginAccount != null) {

            loginAccount.put("sessionId", sessionId);                          // 保存一份 sessionId 到 loginAccount 备用
            CacheKit.put(loginAccountCacheName, sessionId, loginAccount);

            createLoginLog(loginAccount.getUsername(), loginIp);
            return loginAccount;
        }
        return null;
    }

    /**
     * 创建登录日志
     */
    private void createLoginLog(String username, String loginIp) {
        Record loginLog = new Record().set("username", username).set("ip", loginIp).set("date", new Date());
        Db.save("login_log", loginLog);
    }



    /**
     * 退出登录
     */
    public void logout(String sessionId) {
        if (sessionId != null) {
            CacheKit.remove(loginAccountCacheName, sessionId);
            Session.dao.deleteById(sessionId);
        }
    }

    /**
     * 从数据库重新加载登录账户信息
     */
    public void reloadLoginAccount(User loginAccountOld) {
        String sessionId = loginAccountOld.get("sessionId");
        User loginAccount = userDao.findFirst("select * from user where username=? limit 1", loginAccountOld.getUsername());
        loginAccount.put("sessionId", sessionId);        // 保存一份 sessionId 到 loginAccount 备用

        // 集群方式下，要做一通知其它节点的机制，让其它节点使用缓存更新后的数据，
        // 将来可能把 account 用 id : obj 的形式放缓存，更新缓存只需要 CacheKit.remove("account", id) 就可以了，
        // 其它节点发现数据不存在会自动去数据库读取，所以未来可能就是在 AccountService.getById(int id)的方法引入缓存就好
        // 所有用到 account 对象的地方都从这里去取
        CacheKit.put(loginAccountCacheName, sessionId, loginAccount);
    }
}
