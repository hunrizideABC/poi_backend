package com.example.poi.common.config.security;


import com.example.poi.common.component.DynamicSecurityService;

import com.example.poi.model.UmsResource;
import com.example.poi.service.UmsAdminService;
import com.example.poi.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义配置，用于配置如何获取用户信息及动态权限
 * Created by macro on 2019/11/9.
 */
@Configuration
public class PoiSecurityConfig {

    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("PoiSecurityConfig-UserDetailsService");
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                System.out.println("PoiSecurityConfig-DynamicSecurityService");
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.list();
                for (UmsResource resource : resourceList) {
                    System.out.println(resource.getUrl());
                    System.out.println(resource.getId() + ":" + resource.getName());
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;



            }
        };
    }
}
