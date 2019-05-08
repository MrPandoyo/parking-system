package com.yusuf.parkingsystem.controller;

import com.yusuf.parkingsystem.constant.UserType;
import com.yusuf.parkingsystem.dao.MemberDao;
import com.yusuf.parkingsystem.dao.UserDao;
import com.yusuf.parkingsystem.dto.ProfileDto;
import com.yusuf.parkingsystem.entity.Member;
import com.yusuf.parkingsystem.entity.User;
import com.yusuf.parkingsystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired private UserDao userDao;
    @Autowired private MemberDao memberDao;
    @Autowired private ImageService imageService;

    @GetMapping("/profile")
    public String showProfile(ModelMap modelMap, Principal principal){
        ProfileDto profileDto = new ProfileDto();

        User user = userDao.findByUsername(principal.getName());
        profileDto.setUser(user);
        if(user.getUserType().equals(UserType.MEMBER)){
            Member member = memberDao.findByUserUsername(principal.getName());
            profileDto.setIsMember(true);
            profileDto.setMember(member);
        }

        modelMap.addAttribute("profile",profileDto);
        return "profile";
    }

    @PostMapping("/profile")
    public String saveProfile(ProfileDto profile, ModelMap modelMap, RedirectAttributes redirectAttributes, MultipartFile profilePicture, MultipartFile ktpPicture){

        User user = userDao.findById(profile.getUser().getId()).get();
        if(!profilePicture.isEmpty()){
            String fotoProfil = imageService.uploadImage(profilePicture, ImageService.PROFILE);
            user.setPhoto(fotoProfil);
        }
        user.setFullname(profile.getUser().getFullname());
        user.setHp(profile.getUser().getHp());

        try {
            userDao.save(user);
            if (profile.getIsMember()) {
                Member member = memberDao.findById(profile.getMember().getId()).get();
                member.setTanggalLahir(profile.getMember().getTanggalLahir());
                member.setTempatLahir(profile.getMember().getTempatLahir());
                member.setAlamat(profile.getMember().getAlamat());
                member.setNomorKtp(profile.getMember().getNomorKtp());

                if (!ktpPicture.isEmpty()) {
                    String fotoKtp = imageService.uploadImage(ktpPicture, ImageService.KTP);
                    member.setFotoKtp(fotoKtp);
                }
                memberDao.save(member);
            }
            redirectAttributes.addFlashAttribute("successMessage","Profile telah diupdate");
        }catch (Exception e){
            e.getMessage();
            modelMap.addAttribute("errorMessage",e.getMessage());
            modelMap.addAttribute("profile",profile);
            return "profile";
        }
        return "redirect:/profile";
    }

}
