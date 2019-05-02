package com.yusuf.parkingsystem.controller;

import com.yusuf.parkingsystem.constant.UserType;
import com.yusuf.parkingsystem.dao.UserDao;
import com.yusuf.parkingsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired UserDao userDao;

    @GetMapping("/")
    public String showHome(Principal principal){

        User user = userDao.findByUsername(principal.getName());
        if(UserType.MEMBER.equals(user.getUserType())){
            return "redirect:/dashboard_member";
        }else{
            return "dashboard";
        }
    }

}
