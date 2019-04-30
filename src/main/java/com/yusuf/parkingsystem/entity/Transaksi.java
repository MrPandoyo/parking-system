package com.yusuf.parkingsystem.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table
@Entity
public class Transaksi extends BaseEntity{

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_card_id")
    private MemberCard memberCard;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @ManyToOne
    private Harga harga;

    private Boolean paid = Boolean.FALSE;

}
