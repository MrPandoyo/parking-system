package com.parkingsystem.controller;

import com.parkingsystem.dao.*;
import com.parkingsystem.dto.CreateCardDto;
import com.parkingsystem.constant.TipeTransaksi;
import com.parkingsystem.entity.BuktiBayar;
import com.parkingsystem.entity.Member;
import com.parkingsystem.entity.MemberCard;
import com.parkingsystem.entity.Transaksi;
import com.parkingsystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.security.Principal;

@Controller
@RequestMapping("/dashboard_member")
public class DashoardMemberController {

    @Autowired private MemberCardDao memberCardDao;
    @Autowired private MemberDao memberDao;
    @Autowired private HargaDao hargaDao;
    @Autowired private TransaksiDao transaksiDao;
    @Autowired private BuktiBayarDao buktiBayarDao;

    @Autowired private ImageService imageService;

    @GetMapping
    public String showDashboard(Principal principal, ModelMap modelMap){

        modelMap.addAttribute("datas",memberCardDao.findByMemberUserUsername(principal.getName()));
        modelMap.addAttribute("transaksis",transaksiDao.findByPaidAndMemberUserUsername(false,principal.getName()));
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
            String url = "http://localhost:8080/info_card/"+memberCard.getNomorKartu();
            String qrCode = imageService.generateQRCodeImage(url,400,400);
            memberCard.setQrCode(qrCode);
            memberCard = memberCardDao.save(memberCard);

            Transaksi transaksi = new Transaksi();
            transaksi.setMember(member);
            transaksi.setMemberCard(memberCard);
            transaksi.setTipeTransaksi(TipeTransaksi.NEW);
            transaksi.setHarga(cardDto.getHarga());
            transaksi.setNamaBank(cardDto.getNamaBank());
            transaksi.setNamaPemilikRekening(cardDto.getNamaPemilikRekening());
            transaksi.setNomorRekening(cardDto.getNomorRekening());

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

    @GetMapping("/perpanjang_kartu")
    public String showForm(@RequestParam String id, ModelMap modelMap){

        MemberCard card = memberCardDao.findById(id).get();
        Transaksi transaksi = new Transaksi();
        transaksi.setMemberCard(card);
        transaksi.setMember(card.getMember());

        modelMap.addAttribute("transaksi",transaksi);
        modelMap.addAttribute("hargas", hargaDao.findAll());
        return "perpanjang_form";
    }

    @Transactional
    @PostMapping("/perpanjang_kartu")
    public String savePerpanjang(Transaksi transaksi, RedirectAttributes redirectAttributes) {

        MemberCard memberCard = memberCardDao.findById(transaksi.getMemberCard().getId()).get();
        Member member = memberCard.getMember();
        try{
            transaksi.setMember(member);
            transaksi.setMemberCard(memberCard);
            transaksi.setTipeTransaksi(TipeTransaksi.UPDATE);
            transaksiDao.save(transaksi);
            redirectAttributes.addFlashAttribute("transaksi",transaksi);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/dashboard_member/pembuatan_berhasil";
    }

    @GetMapping("/pembuatan_berhasil")
    public String showAfter(){
        return "pembuatan_berhasil";
    }

    @GetMapping("/print_transaksi")
    public String printTrx(@RequestParam String id, ModelMap modelMap) {
        Transaksi transaksi = transaksiDao.findById(id).get();
        modelMap.addAttribute("transaksi",transaksi);
        return "print_trx";
    }

    @GetMapping("/bukti_pembayaran")
    public String showBuktiPembayaran(@RequestParam String id, ModelMap modelMap){

        Transaksi transaksi = transaksiDao.findById(id).get();
        if(transaksi.getBuktiBayarList().isEmpty()){
            BuktiBayar buktiBayar = new BuktiBayar();
            buktiBayar.setTransaksi(transaksi);
            modelMap.addAttribute("bukti",buktiBayar);
        }else{
            modelMap.addAttribute("bukti",transaksi.getBuktiBayarList().get(0));
        }
        return "bukti_pembayaran";
    }

    @PostMapping("/bukti_pembayaran")
    public String postBuktiPembayaran(BuktiBayar bukti, ModelMap modelMap, RedirectAttributes redirectAttributes, MultipartFile photoBukti){

        try{
            if(!photoBukti.isEmpty()){
                String foto = imageService.uploadImage(photoBukti, ImageService.BUKTI_BAYAR);
                bukti.setFoto(foto);
            }
            buktiBayarDao.save(bukti);
            redirectAttributes.addAttribute("successMessage","Bukti pembayaran telah disimpan");
        }catch (Exception e){
            e.printStackTrace();
            modelMap.addAttribute("bukti",bukti);
            modelMap.addAttribute("errorMessage",e.getMessage());
            return "bukti_pembayaran";
        }

        return "redirect:/";
    }

}
