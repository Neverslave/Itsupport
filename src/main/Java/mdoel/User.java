package mdoel;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {

   public static final User userDao = new User().dao();

}
