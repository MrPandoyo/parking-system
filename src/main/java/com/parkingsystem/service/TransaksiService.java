package com.parkingsystem.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransaksiService {

    public BigDecimal generateRandom3Digit(){
        BigDecimal bigDecimal = BigDecimal.ZERO;
        int digit = (int) ((Math.random() * 900) + 100);
        return bigDecimal.add(BigDecimal.valueOf(digit));
    }

}
