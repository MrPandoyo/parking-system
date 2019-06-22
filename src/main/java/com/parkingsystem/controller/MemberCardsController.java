package com.parkingsystem.controller;

import com.parkingsystem.dao.MemberCardDao;
import com.parkingsystem.dao.MemberDao;
import com.parkingsystem.dto.SearchDto;
import com.parkingsystem.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/member_cards")
public class MemberCardsController {

    @Autowired private MemberCardDao memberCardDao;
    @Autowired private MemberDao memberDao;

    @GetMapping
    public String showList(Principal principal, SearchDto params, ModelMap modelMap){

        if(StringUtils.hasText(params.getStatus())){
            if("TRUE".equalsIgnoreCase(params.getStatus())){
                modelMap.addAttribute("cards",memberCardDao.findByExpiredDateAfter(new Date()));
            }else{
                modelMap.addAttribute("cards",memberCardDao.findByExpiredDateBeforeOrExpiredDateIsNull(new Date()));
            }
        }else{
            modelMap.addAttribute("cards",memberCardDao.findAll());
        }

        modelMap.addAttribute("params",params);
        return "member_cards";
    }

    @GetMapping("/print")
    public String print(Principal principal, SearchDto params, ModelMap modelMap){

        if(StringUtils.hasText(params.getStatus())){
            if("TRUE".equalsIgnoreCase(params.getStatus())){
                modelMap.addAttribute("cards",memberCardDao.findByExpiredDateAfter(new Date()));
            }else{
                modelMap.addAttribute("cards",memberCardDao.findByExpiredDateBeforeOrExpiredDateIsNull(new Date()));
            }
        }else{
            modelMap.addAttribute("cards",memberCardDao.findAll());
        }

        modelMap.addAttribute("params",params);
        return "print_member_cards";
    }

}
