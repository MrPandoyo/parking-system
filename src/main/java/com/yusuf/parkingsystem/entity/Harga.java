package com.yusuf.parkingsystem.entity;

import com.yusuf.parkingsystem.constant.DurationType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Table
@Entity
public class Harga extends BaseEntity{

    @NotEmpty
    private String nama;

    @NotNull
    private BigDecimal amount = BigDecimal.ZERO;

    @NotNull
    @Column(length = 1)
    private int durationLength;

    @NotNull
    private DurationType durationType;
}
