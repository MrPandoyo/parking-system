package com.yusuf.parkingsystem.config;

import com.yusuf.parkingsystem.dao.UserDao;
import com.yusuf.parkingsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@ControllerAdvice(basePackages = {"com.yusuf.parkingsystem.controller"})
public class GlobalControllerAdvice {

    @Autowired
    private UserDao userDao;

    @ModelAttribute
    public void globalAttribute(ModelMap mm, Principal principal, HttpServletRequest request){
        if(principal != null){
            User user = userDao.findByUsername(principal.getName());
            if(user != null){
                mm.addAttribute("loggedUser",user);
            }
        }
    }

}
