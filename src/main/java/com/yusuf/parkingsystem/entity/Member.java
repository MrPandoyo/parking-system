package com.yusuf.parkingsystem.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberCard> memberCards = new ArrayList<>();

}
