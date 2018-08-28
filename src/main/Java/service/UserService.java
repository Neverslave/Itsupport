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

   
}
