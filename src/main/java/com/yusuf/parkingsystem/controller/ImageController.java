package com.yusuf.parkingsystem.controller;

import com.yusuf.parkingsystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    private ImageService imgService;

    @GetMapping("/profile/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveProfileImage(@PathVariable String filename) {
        try {
            Resource file = imgService.loadAsResource(filename, ImageService.PROFILE);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping("/ktp/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveKtpImage(@PathVariable String filename) {
        try {
            Resource file = imgService.loadAsResource(filename, ImageService.KTP);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
