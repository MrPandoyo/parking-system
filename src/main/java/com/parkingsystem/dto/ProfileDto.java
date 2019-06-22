package com.parkingsystem.dto;

import com.parkingsystem.entity.Member;
import com.parkingsystem.entity.User;
import lombok.Data;

@Data
public class ProfileDto {
    private User user;
    private Member member;
    private Boolean isMember = Boolean.FALSE;
}
