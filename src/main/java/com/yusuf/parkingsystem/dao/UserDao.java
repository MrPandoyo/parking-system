package com.yusuf.parkingsystem.dao;

import com.yusuf.parkingsystem.constant.UserType;
import com.yusuf.parkingsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, String> {
    User findByUsername(String username);
    Page<User> findByFullnameContainingIgnoreCaseAndUserType(String name, UserType userType, Pageable pageable);
    Page<User> findByUserType(UserType userType, Pageable pageable);
}
