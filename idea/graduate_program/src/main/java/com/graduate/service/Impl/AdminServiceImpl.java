package com.graduate.service.Impl;

import com.graduate.mapper.AdminMapper;
import com.graduate.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public int CountAdmin(int id) {
        return adminMapper.CountAdmin(id);
    }
}
