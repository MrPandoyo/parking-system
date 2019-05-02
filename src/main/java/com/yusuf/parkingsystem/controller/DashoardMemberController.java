package com.yusuf.parkingsystem.controller;

import com.yusuf.parkingsystem.constant.TipeTransaksi;
import com.yusuf.parkingsystem.dao.HargaDao;
import com.yusuf.parkingsystem.dao.MemberCardDao;
import com.yusuf.parkingsystem.dao.MemberDao;
import com.yusuf.parkingsystem.dao.TransaksiDao;
import com.yusuf.parkingsystem.dto.CreateCardDto;
import com.yusuf.parkingsystem.entity.Member;
import com.yusuf.parkingsystem.entity.MemberCard;
import com.yusuf.parkingsystem.entity.Transaksi;
import com.yusuf.parkingsystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@RequestMapping("/dashboard_member")
public class DashoardMemberController {

    @Autowired private MemberCardDao memberCardDao;
    @Autowired private MemberDao memberDao;
    @Autowired private HargaDao hargaDao;
    @Autowired private TransaksiDao transaksiDao;

    @Autowired private ImageService imageService;

    @GetMapping
    public String showDashboard(Principal principal, ModelMap modelMap){
        modelMap.addAttribute("datas",memberCardDao.findByMemberUserUsername(principal.getName()));
        return "dashboard_member";
    }

    @GetMapping("/tambah_kartu")
    public String showForm(ModelMap modelMap){
        modelMap.addAttribute("cardDto",new CreateCardDto());
        modelMap.addAttribute("hargas", hargaDao.findAll());
        return "card_form";
    }

    @Transactional
    @PostMapping("/tambah_kartu")
    public String saveForm(Principal principal, CreateCardDto cardDto, ModelMap modelMap, RedirectAttributes redirectAttributes){

        Member member = memberDao.findByUserUsername(principal.getName());

        MemberCard memberCard = new MemberCard();
        memberCard.setMember(member);
        memberCard.setTipeKendaraan(cardDto.getTipeKendaraan());
        memberCard.setNomorPlat(cardDto.getNomorPlat());

        try {
            String qrCode = imageService.generateQRCodeImage(memberCard.getNomorKartu(),400,400);
            memberCard.setQrCode(qrCode);
            memberCard = memberCardDao.save(memberCard);

            Transaksi transaksi = new Transaksi();
            transaksi.setMember(member);
            transaksi.setMemberCard(memberCard);
            transaksi.setTipeTransaksi(TipeTransaksi.NEW);
            transaksi.setHarga(cardDto.getHarga());

            transaksi = transaksiDao.save(transaksi);
            redirectAttributes.addFlashAttribute("transaksi",transaksi);
        }catch (Exception e){
            e.printStackTrace();
            modelMap.addAttribute("errorMessage",e.getMessage());
            modelMap.addAttribute("cardDto",new CreateCardDto());
            modelMap.addAttribute("hargas", hargaDao.findAll());
        }

        return "redirect:/dashboard_member/pembuatan_berhasil";
    }

    @GetMapping("/pembuatan_berhasil")
    public String showAfter(){
        return "pembuatan_berhasil";
    }

}
