package com.yusuf.parkingsystem.dao;

import com.yusuf.parkingsystem.entity.MemberCard;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MemberCardDao extends PagingAndSortingRepository<MemberCard, String> {

    List<MemberCard> findByMemberUserUsername(String username);
    MemberCard findByNomorKartu(String nomorKartu);

}
