/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yusuf.parkingsystem.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table
public class UserPassword extends BaseEntity {

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user", nullable = false, columnDefinition = "varchar(36)")
    private @Getter
    @Setter
    User user;

    @NotEmpty(message = "Password tidak boleh kosong")
    @Column
    private @Getter
    @Setter
    String password;

    public UserPassword(){}

    public UserPassword(User user, String password) {
        this.user = user;
        this.password = password;
    }

}
