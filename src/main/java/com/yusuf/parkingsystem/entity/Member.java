package com.yusuf.parkingsystem.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table
@Entity
public class Member extends BaseEntity {

    @OneToOne
    private User user;

    @NotEmpty
    @Column(unique = true)
    private String nomorKtp;

    @NotEmpty
    private String fotoKtp;

    @NotEmpty
    private String alamat;

    @NotEmpty
    private String tempatLahir;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggalLahir;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberCard> memberCards = new ArrayList<>();

}
