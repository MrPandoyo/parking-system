/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yusuf.parkingsystem.entity;

import com.yusuf.parkingsystem.constant.UserType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
@Table
public class User extends BaseEntity {
    @Column(nullable = false,unique = true)
    private String username;
    
    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;
    
    @NotEmpty
    private String password;

    private String hp;

    @NotEmpty
    private String fullname;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String photo;

    public String getStatusAktif(){
        if(this.active){
            return "Aktif";
        }
        return "Tidak Aktif";
    }

    public User(){ }

    public User(String username, String password, String fullname, String hp,  UserType userType){
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.hp = hp;
        this.userType = userType;
    }

}
