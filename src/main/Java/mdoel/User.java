package mdoel;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> implements IBean {

   String username;
   String password;
   String createTime;
   String lastloginTime;
   String isAdmin;

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getCreateTime() {
      return createTime;
   }

   public void setCreateTime(String createTime) {
      this.createTime = createTime;
   }

   public String getLastloginTime() {
      return lastloginTime;
   }

   public void setLastloginTime(String lastloginTime) {
      this.lastloginTime = lastloginTime;
   }

   public String getIsAdmin() {
      return isAdmin;
   }

   public void setIsAdmin(String isAdmin) {
      this.isAdmin = isAdmin;
   }
}