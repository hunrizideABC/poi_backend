package com.example.poi.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.poi.mapper.PoiCheckMapper;
import com.example.poi.mapper.UmsAdminRoleRelationMapper;
import com.example.poi.model.PoiCheck;

import com.example.poi.service.PoiCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * poi审核表 服务实现类
 * </p>
 *
 * @author test
 * @since 2022-09-03
 */
@Service
public class PoiCheckServiceImpl extends ServiceImpl<PoiCheckMapper, PoiCheck> implements PoiCheckService {

    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Override
    public boolean create(PoiCheck poiCheck) {
        poiCheck.setCreateTime(new Date());
        return save(poiCheck);
    }

    @Override
    public boolean createbatch(List<PoiCheck> poiChecklist, int src) {
        List<Long> adminIds = umsAdminRoleRelationMapper.getAdminIdListBySrc(src);
        double poi_size = poiChecklist.size();
        double admin_size = adminIds.size();
        System.out.println(admin_size);
        int batch_size = (int)Math.ceil(poi_size/admin_size);
        for(int i=0;i<admin_size;i++){
            Long adminId = adminIds.get(i);
            int begin = i*batch_size;
            int end = (i+1)*batch_size;
            if(end>(int)poi_size){
                end = (int)poi_size;
            }
            for(int j=begin;j<end;j++){
                PoiCheck poiCheck = poiChecklist.get(j);
                poiCheck.setAdminId(adminId);
                poiCheck.setCreateTime(new Date());
            }
        }
        return saveBatch(poiChecklist);
    }

    @Override
    public boolean createbatch(List<PoiCheck> poiChecklist, int src, int batchsize) {
        List<Long> adminIds = umsAdminRoleRelationMapper.getAdminIdListBySrc(src);
        double poi_size = poiChecklist.size();
        double admin_size = adminIds.size();
        System.out.println(admin_size);
        int batch_size = (int)Math.ceil(poi_size/admin_size);
        for(int i=0;i<admin_size;i++){
            Long adminId = adminIds.get(i);
            int begin = i*batch_size;
            int end = (i+1)*batch_size;
            if(end>(int)poi_size){
                end = (int)poi_size;
            }
            for(int j=begin;j<end;j++){
                PoiCheck poiCheck = poiChecklist.get(j);
                poiCheck.setAdminId(adminId);
                poiCheck.setCreateTime(new Date());
            }
        }
        return saveBatch(poiChecklist,batchsize);
    }

    @Override
    public boolean update(Long id, PoiCheck poiCheck) {
        poiCheck.setId(id);
        boolean success = updateById(poiCheck);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        return success;
    }

    @Override
    public boolean delete(List<Long> ids) {
        boolean success = removeByIds(ids);
        return success;
    }

    @Override
    public Page<PoiCheck> list(Long adminId, Integer status, Integer pageSize, Integer pageNum) {
        Page<PoiCheck> page = new Page<>(pageNum,pageSize);
        QueryWrapper<PoiCheck> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PoiCheck> lambda = wrapper.lambda();
        Integer src =  umsAdminRoleRelationMapper.getSrcByAdminId(adminId);
        lambda.like(PoiCheck::getStatus,status);
        if(!src.equals(0)){
            lambda.like(PoiCheck::getSrc,src);
            lambda.like(PoiCheck::getAdminId,adminId);
        }
        lambda.orderByDesc(PoiCheck::getCreateTime);
        return page(page,wrapper);
    }


}
