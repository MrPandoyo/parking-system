package com.parkingsystem.controller;

import com.parkingsystem.dao.MemberCardDao;
import com.parkingsystem.constant.UserType;
import com.parkingsystem.dao.UserDao;
import com.parkingsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired UserDao userDao;
    @Autowired
    MemberCardDao memberCardDao;

    @GetMapping("/")
    public String showHome(Principal principal){

        User user = userDao.findByUsername(principal.getName());
        if(UserType.MEMBER.equals(user.getUserType())){
            return "redirect:/dashboard_member";
        }else{
            return "dashboard";
        }
    }

    @GetMapping("/info_card/{kode}")
    public String showInfo(@PathVariable String kode, ModelMap modelMap){
        modelMap.addAttribute("card",memberCardDao.findByNomorKartu(kode));
        return "info_card";
    }

}
