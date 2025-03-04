package com.parkingsystem.controller;

import com.parkingsystem.constant.UserType;
import com.parkingsystem.dao.TransaksiDao;
import com.parkingsystem.dao.UserDao;
import com.parkingsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/laporan_transaksi")
public class LaporanTransaksiController {

    @Autowired private UserDao userDao;
    @Autowired private TransaksiDao transaksiDao;

    @GetMapping
    public String showHistori(Principal principal, ModelMap modelMap, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){

        User user = userDao.findByUsername(principal.getName());
        if(user.getUserType().equals(UserType.ADMIN)){
            modelMap.addAttribute("datas",transaksiDao.findByPaid(true,pageable));
        }else{
            modelMap.addAttribute("datas",transaksiDao.findByPaidAndMemberUserId(true,user.getId(),pageable));
        }

        return "laporan_transaksi";
    }

    @GetMapping("/print")
    public String print(Principal principal, ModelMap modelMap, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){

        User user = userDao.findByUsername(principal.getName());
        if(user.getUserType().equals(UserType.ADMIN)){
            modelMap.addAttribute("datas",transaksiDao.findByPaid(true,pageable));
        }else{
            modelMap.addAttribute("datas",transaksiDao.findByPaidAndMemberUserId(true,user.getId(),pageable));
        }

        return "print_laporan_trx";
    }

}
