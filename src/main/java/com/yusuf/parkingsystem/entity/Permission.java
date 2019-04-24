/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yusuf.parkingsystem.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Permission extends BaseEntity {

    @NotEmpty(message = "Label tidak boleh kosong")
    @Column(name = "permission_label", nullable = false)
    private String label;

    @NotEmpty(message = "Value tidak boleh kosong")
    @Column(name = "permission_value", nullable = false, unique = true)
    private String value;

}
