package com.yusuf.parkingsystem.dao;

import com.yusuf.parkingsystem.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, String> {
    User findByUsername(String username);
}
