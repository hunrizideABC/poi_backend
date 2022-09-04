package com.example.poi.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.poi.mapper.PoiUnionMapper;
import com.example.poi.model.PoiUnion;
import com.example.poi.service.PoiUnionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * poi统一库 服务实现类
 * </p>
 *
 * @author test
 * @since 2022-09-03
 */
@Service
public class PoiUnionServiceImpl extends ServiceImpl<PoiUnionMapper, PoiUnion> implements PoiUnionService {

    @Override
    public boolean create(PoiUnion poiUnion) {
        poiUnion.setCreateTime(new Date());
        return save(poiUnion);
    }

    @Override
    public boolean createbatch(List<PoiUnion> poiUnionlist) {
        for(PoiUnion poiUnion:poiUnionlist){
            poiUnion.setCreateTime(new Date());
        }
        return saveBatch(poiUnionlist);
    }

    @Override
    public boolean createbatch(List<PoiUnion> poiUnionlist, int batchsize) {
        for(PoiUnion poiUnion:poiUnionlist){
            poiUnion.setCreateTime(new Date());
        }
        return saveBatch(poiUnionlist,batchsize);
    }

    @Override
    public boolean update(Long id, PoiUnion poiUnion) {
        poiUnion.setId(id);
        boolean success = updateById(poiUnion);
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
    public Page<PoiUnion> list(String displaynameKeyword, String addressKeyword, Long poi_id, Integer pageSize, Integer pageNum) {
        QueryWrapper<PoiUnion> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PoiUnion> lambda = wrapper.lambda();
        if(!poi_id.equals(-1l)){
            lambda.eq(PoiUnion::getId,poi_id);
            pageSize = 1;
            pageNum = 1;
        }
        Page<PoiUnion> page = new Page<>(pageNum,pageSize);
        if(StrUtil.isNotEmpty(displaynameKeyword)){
            lambda.like(PoiUnion::getDisplayname,displaynameKeyword);
        }
        if(StrUtil.isNotEmpty(addressKeyword)){
            lambda.like(PoiUnion::getAddress,addressKeyword);
        }

        return page(page,wrapper);
    }
}
