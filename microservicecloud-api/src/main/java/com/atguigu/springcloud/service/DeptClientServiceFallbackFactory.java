package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

//Component记得添加
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService>{

    @Override
    public DeptClientService create(Throwable throwable) {

        return new DeptClientService(){



            @Override
            public Dept get(Long id){
                Dept dept=new Dept();
                dept.setDeptno(id);
                dept.setDname("该id"+id+"没有对应的信息null，processHystrix_Get客户端降级处理，");
                dept.setDb_source("gcr没有这个库在mysql");
                return dept;
            }

            @Override
            public boolean add(Dept dept){
                return false;
            }

            @Override
            public List<Dept> list(){
                return null;
            }
        };



    }
}
