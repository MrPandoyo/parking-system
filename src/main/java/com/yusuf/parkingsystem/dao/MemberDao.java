package com.yusuf.parkingsystem.dao;

import com.yusuf.parkingsystem.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberDao extends PagingAndSortingRepository<Member,String> {
    Member findByNomorKtp(String ktp);
    Member findByUserUsername(String name);
    Page<Member> findByUserFullnameContainingIgnoreCase(String name, Pageable pageable);
}
