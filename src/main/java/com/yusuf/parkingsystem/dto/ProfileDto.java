package com.yusuf.parkingsystem.dto;

import com.yusuf.parkingsystem.entity.Member;
import com.yusuf.parkingsystem.entity.User;
import lombok.Data;

@Data
public class ProfileDto {
    private User user;
    private Member member;
    private Boolean isMember = Boolean.FALSE;
}
