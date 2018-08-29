package service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.json.Json;
import model.User;

import java.util.List;

public class UserService {
    User userDao = new User().dao();
    List<User> users;
    public String getUsers(){

        users= userDao.find("select * from user");
        String usersJson = JSON.toJSONString(users);

         return usersJson;

    }
    public boolean addUsers(User user){
        if(user.dao().save()){

            return true;
        }else{
            return false;
        }


    }
    public boolean deleteUser(User user){

        if(user.dao().delete()){
            return true;
        }else  {
            return false;
        }
    }
    public boolean editUser(User user){
        user.dao().setPassword(user.getPassword());
        return true;
    }

   
}
