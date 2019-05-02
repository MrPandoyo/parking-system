package com.yusuf.parkingsystem.dao;

import com.yusuf.parkingsystem.entity.Harga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HargaDao extends PagingAndSortingRepository<Harga, String> {

    Page<Harga> findByNamaContainingIgnoreCase(String nama, Pageable pageable);

}
