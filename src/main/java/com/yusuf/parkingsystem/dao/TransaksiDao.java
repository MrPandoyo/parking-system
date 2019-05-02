package com.yusuf.parkingsystem.dao;

import com.yusuf.parkingsystem.entity.Transaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransaksiDao extends PagingAndSortingRepository<Transaksi, String> {

    Page<Transaksi> findByMemberUserFullnameContainingIgnoreCaseAndPaid(String name,Boolean paid, Pageable pageable);
    Page<Transaksi> findByPaid(Boolean paid, Pageable pageable);

}
