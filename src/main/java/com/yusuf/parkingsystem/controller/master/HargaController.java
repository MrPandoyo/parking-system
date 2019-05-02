package com.yusuf.parkingsystem.controller.master;

import com.yusuf.parkingsystem.dao.HargaDao;
import com.yusuf.parkingsystem.entity.Harga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/master/harga")
public class HargaController {

    @Autowired
    private HargaDao hargaDao;

    @GetMapping
    public String showList(@RequestParam(required = false) String value, ModelMap modelMap, @PageableDefault Pageable pageable){

        if(StringUtils.hasText(value)){
            modelMap.addAttribute("datas",hargaDao.findByNamaContainingIgnoreCase(value,pageable));
        }else{
            modelMap.addAttribute("datas",hargaDao.findAll(pageable));
        }
        modelMap.addAttribute("value",value);
        return "master/harga/list";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) String id, ModelMap modelMap){
        Harga harga = new Harga();

        if(StringUtils.hasText(id)){
            harga = hargaDao.findById(id).get();
        }
        modelMap.addAttribute("harga",harga);
        return "master/harga/form";
    }

    @PostMapping("/form")
    public String saveForm(Harga harga, ModelMap modelMap, RedirectAttributes redirectAttributes){

        try {
            hargaDao.save(harga);
            redirectAttributes.addFlashAttribute("successMessage","Data telah disimpan");
        }catch (Exception e){
            e.printStackTrace();
            modelMap.addAttribute("errorMessage",e.getMessage());
            modelMap.addAttribute("harga",harga);
            return "master/harga/form";
        }

        return "redirect:/master/harga";
    }

}
