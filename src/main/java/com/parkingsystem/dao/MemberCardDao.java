package com.parkingsystem.dao;

import com.parkingsystem.entity.MemberCard;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface MemberCardDao extends PagingAndSortingRepository<MemberCard, String> {

    List<MemberCard> findByMemberUserUsername(String username);
    MemberCard findByNomorKartu(String nomorKartu);

    List<MemberCard> findByExpiredDateBeforeOrExpiredDateIsNull(Date date);
    List<MemberCard> findByExpiredDateAfter(Date date);

    List<MemberCard> findByMemberUserUsernameAndExpiredDateBeforeOrExpiredDateIsNull(String username, Date date);
    List<MemberCard> findByMemberUserUsernameAndExpiredDateAfter(String username, Date date);
}
