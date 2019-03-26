package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.DeptDao;
import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptServcie{


    @Autowired
    private DeptDao deptDao;

    @Override
    public boolean add(Dept dept) {

        boolean b = deptDao.addDept(dept);
        System.out.println(b);
        return b;
    }

    @Override
    public Dept get(Long id) {
        Dept dept = deptDao.findById(id);
        return dept;
    }

    @Override
    public List<Dept> list() {
        List<Dept> list = deptDao.findAll();
        return list;
    }
}
