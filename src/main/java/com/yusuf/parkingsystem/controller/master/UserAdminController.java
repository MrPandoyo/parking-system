package com.yusuf.parkingsystem.controller.master;

import com.yusuf.parkingsystem.constant.UserType;
import com.yusuf.parkingsystem.dao.UserDao;
import com.yusuf.parkingsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/master/user_admin")
public class UserAdminController {

    @Autowired private UserDao userDao;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showList(@RequestParam(required = false) String value, ModelMap modelMap, @PageableDefault Pageable pageable){

        if(StringUtils.hasText(value)){
            modelMap.addAttribute("datas",userDao.findByFullnameContainingIgnoreCaseAndUserType(value,UserType.ADMIN,pageable));
        }else{
            modelMap.addAttribute("datas",userDao.findByUserType(UserType.ADMIN,pageable));
        }
        modelMap.addAttribute("value",value);
        return "master/user_admin/list";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) String id, ModelMap modelMap){
        User user = new User();

        if(StringUtils.hasText(id)){
            user = userDao.findById(id).get();
        }
        modelMap.addAttribute("user",user);
        return "master/user_admin/form";
    }

    @PostMapping("/form")
    public String saveForm(User user, ModelMap modelMap, RedirectAttributes redirectAttributes){

        if(StringUtils.hasText(user.getId())){
            if(StringUtils.hasText(user.getPassword())){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }else{
            if(!StringUtils.hasText(user.getPassword())){
                modelMap.addAttribute("errorMessage","Password harus diisi");
                modelMap.addAttribute("user",user);
                return "master/user_admin/form";
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserType(UserType.ADMIN);
        }

        try {
            userDao.save(user);
            redirectAttributes.addFlashAttribute("successMessage","Data telah disimpan");
        }catch (Exception e){
            e.printStackTrace();
            modelMap.addAttribute("errorMessage",e.getMessage());
            modelMap.addAttribute("user",user);
            return "master/user_admin/form";
        }

        return "redirect:/master/user_admin";
    }

}
