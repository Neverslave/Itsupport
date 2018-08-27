package mdoel;

import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

public class LoginLog extends Model<LoginLog> {
    String username;
    String ip;
    Date date ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
