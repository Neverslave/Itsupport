package controller;

import com.jfinal.core.Controller;

public class   IndexController extends Controller{
    public void index(){

    }

    public void login(){

        renderTemplate("/admin/login.html");


    }


}