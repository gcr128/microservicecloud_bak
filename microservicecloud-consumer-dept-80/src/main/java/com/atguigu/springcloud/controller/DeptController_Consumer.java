package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class DeptController_Consumer {

//    private static final String RESTFUL_PRE_URL = "http://localhost:8001";
    private static final String RESTFUL_PRE_URL = "http://MICROSERVICECLOUD-DEPT";//做负载 取服务名



    /*使用restTemplate访问restful接口非常的简单粗暴无脑
    （url, requestMap, ResponseBean.class）这三个参数分别代表
    REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。*/

    @Autowired
    RestTemplate restTemplate;

   @RequestMapping(value = "/consumer/dept/add")
    public boolean add(Dept dept){

       return restTemplate.postForObject(RESTFUL_PRE_URL+"/dept/add",dept,Boolean.class);

    }

    @RequestMapping(value="/consumer/dept/get/{id}")
    public Dept get(@PathVariable Long id){
        return restTemplate.getForObject(RESTFUL_PRE_URL+"/dept/get/"+id,Dept.class);

}

    @RequestMapping(value="/consumer/dept/list")
    public List<Dept> list(){

        return restTemplate.getForObject(RESTFUL_PRE_URL+"/dept/list",List.class);
    }



}