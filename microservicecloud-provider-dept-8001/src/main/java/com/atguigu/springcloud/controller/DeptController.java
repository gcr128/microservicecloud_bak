package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptServcie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptServcie deptServcie;

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping(value="/dept/add",method = RequestMethod.POST)
    public boolean add(@RequestBody Dept dept){
    //public boolean add(@RequestBody Dept dept){
      System.out.println(dept.toString());
      return deptServcie.add(dept);

    }



    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    @RequestMapping(value="/dept/get/{id}",method = RequestMethod.GET)
    public Dept get(@PathVariable Long id){

        Dept dept = deptServcie.get(id);
//        if(null==dept){
//            throw new RuntimeException("该ID"+id+"没有对应的信息");
//        }
        return dept;

    }

    public Dept processHystrix_Get(@PathVariable Long id){
        Dept dept=new Dept();
        dept.setDeptno(id);
        dept.setDname("该id"+id+"没有对应的信息null，processHystrix_Get");
        dept.setDb_source("没有这个库在mysql");
        return dept;
    }


    @RequestMapping(value="/dept/list",method = RequestMethod.GET)
    public List<Dept> list(){

        return deptServcie.list();
    }


    @RequestMapping(value="/dept/discovery",method = RequestMethod.GET)
    public Object discovery(){

        List<String> list = discoveryClient.getServices();
        System.out.println("**********"+list);
        System.out.println("----------------"+list);


        //ctrl+shift+u  小写转大写转换快捷键
        List<ServiceInstance> instances = discoveryClient.getInstances("MICROSERVICECLOUD-DEPT");
         for(ServiceInstance serviceInstance:instances){
             System.out.println(serviceInstance.getServiceId()+"\t"
                     +serviceInstance.getHost()+"\t"
                     +serviceInstance.getPort()+"\t"
                     +serviceInstance.getUri()
             );

         }

//         **********[microservicecloud-dept]
//        ----------------
//                MICROSERVICECLOUD-DEPT	192.168.0.101	8001	http://192.168.0.101:8001
        return this.discoveryClient;

//        {"services":["microservicecloud-dept"],
//          "localServiceInstance":{"host":"192.168.0.101","port":8001,
//                "serviceId":"microservicecloud-dept","uri":"http://192.168.0.101:8001",
//                "metadata":{},"secure":false}}
    }

}
