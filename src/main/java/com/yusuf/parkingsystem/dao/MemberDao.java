package com.yusuf.parkingsystem.dao;

import com.yusuf.parkingsystem.entity.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberDao extends PagingAndSortingRepository<Member,String> {
    Member findByNomorKtp(String ktp);
}
