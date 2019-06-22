package com.parkingsystem.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RegisterDto {

    private String fullname;
    private String email;
    private String alamat;
    private String hp;
    private String jenisKelamin;
    private String noKtp;
    private String photoKtp;
    private String password;

    private String tempatLahir;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggalLahir;

}
