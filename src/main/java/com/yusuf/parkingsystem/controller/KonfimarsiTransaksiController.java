package com.yusuf.parkingsystem.controller;

import com.yusuf.parkingsystem.constant.DurationType;
import com.yusuf.parkingsystem.constant.TipeTransaksi;
import com.yusuf.parkingsystem.dao.TransaksiDao;
import com.yusuf.parkingsystem.entity.MemberCard;
import com.yusuf.parkingsystem.entity.Transaksi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;

@Controller
@RequestMapping("/konfirmasi")
public class KonfimarsiTransaksiController {

    @Autowired private TransaksiDao transaksiDao;

    @GetMapping
    public String showList(@RequestParam(required = false) String value, ModelMap modelMap, @PageableDefault Pageable pageable){

        if(StringUtils.hasText(value)){
            modelMap.addAttribute("datas",transaksiDao.findByMemberUserFullnameContainingIgnoreCaseAndPaid(value,false,pageable));
        }else{
            modelMap.addAttribute("datas",transaksiDao.findByPaid(false,pageable));
        }
        modelMap.addAttribute("value",value);
        return "konfirmasi_pembayaran";
    }

    @GetMapping("/{trx}")
    public String konfirmasiPembayaran(@PathVariable String trx, RedirectAttributes redirectAttributes){

        Transaksi transaksi= transaksiDao.findById(trx).get();
        transaksi.setPaid(true);
        updateCard(transaksi);
        try{
            transaksiDao.save(transaksi);
            redirectAttributes.addFlashAttribute("successMessage","Transaksi Member ["+transaksi.getMember().getUser().getFullname()+"] telah berhasil");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage","Transaksi Member ["+transaksi.getMember().getUser().getFullname()+"] gagal karena "+e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/konfirmasi";
    }

    public void updateCard(Transaksi transaksi){
        int interval = transaksi.getHarga().getDurationLength();
        DurationType type = transaksi.getHarga().getDurationType();

        Calendar cal = Calendar.getInstance();
        if(transaksi.getTipeTransaksi().equals(TipeTransaksi.NEW)){
            cal.setTime(transaksi.getMemberCard().getCreatedDate());
        }else{
            cal.setTime(transaksi.getMemberCard().getExpiredDate());
        }

        if(type.equals(DurationType.MONTH)){
            cal.add(Calendar.MONTH,interval);
        }else{
            cal.add(Calendar.YEAR, interval);
        }
        transaksi.getMemberCard().setExpiredDate(cal.getTime());
    }

}
