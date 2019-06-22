package com.parkingsystem.entity;

import com.parkingsystem.constant.DurationType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    private int durationLength;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DurationType durationType;
}
