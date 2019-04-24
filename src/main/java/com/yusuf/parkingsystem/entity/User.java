/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yusuf.parkingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yusuf.parkingsystem.constant.UserType;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity 
@Table
public class User extends BaseEntity {
    @Column(nullable = false,unique = true)
    private String username;
    
    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;
    
    @Transient
    private String password;

    @Column(unique = true)
    private String hp;

    private String fullname;
        
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy = "user", optional = true)
    @Cascade(CascadeType.ALL)
    private UserPassword userPassword;

    @ManyToMany
    @OrderBy("value asc")
    @JoinTable(
        name="user_permission",
        joinColumns=@JoinColumn(name="id_user", nullable=false),
        inverseJoinColumns=@JoinColumn(name="id_permission", nullable=false)
    )
    private Set<Permission> permissionSet = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    private String photo;

    public String getStatusAktif(){
        if(this.active){
            return "Aktif";
        }
        return "Tidak Aktif";
    }

    public User(){ }

    public User(String username, String email, String fullname, String hp, Boolean active){
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.hp = hp;
        this.active = active;
    }

}
