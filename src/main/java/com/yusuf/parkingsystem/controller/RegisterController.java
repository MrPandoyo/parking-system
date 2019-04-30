package com.yusuf.parkingsystem.controller;

import com.yusuf.parkingsystem.constant.UserType;
import com.yusuf.parkingsystem.dao.MemberDao;
import com.yusuf.parkingsystem.dao.UserDao;
import com.yusuf.parkingsystem.dto.RegisterDto;
import com.yusuf.parkingsystem.entity.Member;
import com.yusuf.parkingsystem.entity.User;
import com.yusuf.parkingsystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;

@Controller
public class RegisterController {

    @Autowired private UserDao userDao;
    @Autowired private MemberDao memberDao;

    @Autowired private ImageService imageService;
    @Autowired private PasswordEncoder passwordEncoder;

    private String FORM = "register";

    @GetMapping("/register")
    public String showRegister(ModelMap modelMap){
        modelMap.addAttribute("registerDto", new RegisterDto());
        return FORM;
    }

    @Transactional
    @PostMapping("/register")
    public String saveRegister(RegisterDto registerDto, ModelMap modelMap, RedirectAttributes redirectAttributes, MultipartFile filePicture) {

        User dupeUser = userDao.findByUsername(registerDto.getEmail());
        if(dupeUser != null){
            modelMap.addAttribute("errorMessage","Email sudah terdaftar");
            modelMap.addAttribute("registerDto",registerDto);
            return FORM;
        }

        Member dupeMember = memberDao.findByNomorKtp(registerDto.getNoKtp());
        if(dupeMember != null){
            modelMap.addAttribute("errorMessage","Nomor KTP sudah terdaftar");
            modelMap.addAttribute("registerDto",registerDto);
            return FORM;
        }

        try {
            String photoKtp = imageService.uploadImage(filePicture, ImageService.KTP);
            String encPwd = passwordEncoder.encode(registerDto.getPassword());

            User user = new User(registerDto.getEmail(),encPwd,registerDto.getFullname(),registerDto.getHp(),UserType.MEMBER);
            userDao.save(user);

            Member member = new Member();
            member.setUser(user);
            member.setNomorKtp(registerDto.getNoKtp());
            member.setFotoKtp(photoKtp);
            memberDao.save(member);

            redirectAttributes.addFlashAttribute("successMessage","Daftar Berhasil");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
            modelMap.addAttribute("registerDto",registerDto);
            return FORM;
        }

        return "redirect:/login";
    }

}
