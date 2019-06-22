package com.parkingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkingsystem.constant.TipeKendaraan;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

@Data
@Table
@Entity
public class MemberCard extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private TipeKendaraan tipeKendaraan;

    @NotEmpty
    @Column(unique = true)
    private String nomorPlat;

    @NotEmpty
    @Column(unique = true, length = 16)
    private String nomorKartu = UUID.randomUUID().toString().replaceAll("-","").substring(0,15);

    @NotEmpty
    @Column(unique = true)
    private String qrCode;

    @Temporal(TemporalType.DATE)
    private Date createdDate = new Date();

    @Temporal(TemporalType.DATE)
    private Date expiredDate;

    public Boolean getActive(){
        if(this.expiredDate != null && this.expiredDate.compareTo(new Date()) > 0){
            return true;
        }else {
            return false;
        }
    }

}
