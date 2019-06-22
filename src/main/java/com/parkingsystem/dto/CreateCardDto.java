package com.parkingsystem.dto;

import com.parkingsystem.constant.TipeKendaraan;
import com.parkingsystem.entity.Harga;
import com.parkingsystem.entity.Member;
import lombok.Data;

@Data
public class CreateCardDto {

    private Member member;
    private TipeKendaraan tipeKendaraan;
    private String nomorPlat;
    private Harga harga;
    private String namaBank;
    private String namaPemilikRekening;
    private String nomorRekening;

}
