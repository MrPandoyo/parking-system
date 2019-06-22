package com.parkingsystem.controller.master;

import com.parkingsystem.constant.UserType;
import com.parkingsystem.dao.MemberDao;
import com.parkingsystem.dao.UserDao;
import com.parkingsystem.entity.Member;
import com.parkingsystem.entity.User;
import com.parkingsystem.service.ImageService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/master/user_member")
public class MemberController {

    @Autowired private MemberDao memberDao;
    @Autowired private UserDao userDao;
    @Autowired private ImageService imageService;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showList(@RequestParam(required = false) String value, ModelMap modelMap, @PageableDefault Pageable pageable){

        if(StringUtils.hasText(value)){
            modelMap.addAttribute("datas",memberDao.findByUserFullnameContainingIgnoreCase(value,pageable));
        }else{
            modelMap.addAttribute("datas",memberDao.findAll(pageable));
        }
        modelMap.addAttribute("value",value);
        return "master/user_member/list";
    }

    @GetMapping("/detail")
    public String showDetail(@RequestParam String id, ModelMap modelMap){
        Member member = memberDao.findById(id).get();
        modelMap.addAttribute("member",member);
        return "master/user_member/detail";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) String id, ModelMap modelMap){
        Member member = new Member();
        if(StringUtils.hasText(id)){
            member = memberDao.findById(id).get();
        }
        modelMap.addAttribute("member",member);
        return "master/user_member/form";
    }

    @Transactional
    @PostMapping("/form")
    public String saveForm(Member member, ModelMap modelMap, RedirectAttributes redirectAttributes, MultipartFile filePicture){

        User user = member.getUser();
        if(StringUtils.hasText(member.getId())){
            if(StringUtils.hasText(user.getPassword())){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }else{
            if(!StringUtils.hasText(user.getPassword())){
                modelMap.addAttribute("errorMessage","Password harus diisi");
                modelMap.addAttribute("member",member);
                return "master/user_member/form";
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserType(UserType.MEMBER);
        }

        try {
            String photoKtp = imageService.uploadImage(filePicture, ImageService.KTP);

            user = userDao.save(user);
            member.setFotoKtp(photoKtp);
            member.setUser(user);
            memberDao.save(member);
            redirectAttributes.addFlashAttribute("successMessage","Data telah disimpan");
        }catch (Exception e){
            e.printStackTrace();
            modelMap.addAttribute("errorMessage",e.getMessage());
            modelMap.addAttribute("member",member);
            return "master/user_member/form";
        }

        return "redirect:/master/user_member";
    }
    
}
