package com.yusuf.parkingsystem.dto;

import com.yusuf.parkingsystem.constant.TipeKendaraan;
import com.yusuf.parkingsystem.entity.Harga;
import com.yusuf.parkingsystem.entity.Member;
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
