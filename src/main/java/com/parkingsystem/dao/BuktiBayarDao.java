package com.parkingsystem.dao;

import com.parkingsystem.entity.BuktiBayar;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BuktiBayarDao extends PagingAndSortingRepository<BuktiBayar, String> {
    BuktiBayar findByTransaksiId(String id);
}
