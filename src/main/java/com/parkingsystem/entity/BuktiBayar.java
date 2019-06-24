package com.parkingsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Table
@Entity
public class BuktiBayar extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "transaksi_id")
    private Transaksi transaksi;

    private String foto;

    private String comment;
}
