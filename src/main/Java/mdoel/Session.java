package mdoel;


import com.jfinal.plugin.activerecord.Model;

public class Session extends Model<Session> {
    private static final long serialVersionUID = 1L;
    public static final Session dao = new Session().dao();
    public String  id ;
    String username;
    Long expireAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    /**
     * 登录会话是否已过期
     */
    public boolean isExpired() {
        return getExpireAt() < System.currentTimeMillis();
    }

    /**
     * 登录会话是否未过期
     */
    public boolean notExpired() {
        return ! isExpired();
    }

}



