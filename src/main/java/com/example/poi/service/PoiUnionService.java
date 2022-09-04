package com.example.poi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.poi.model.PoiUnion;
import com.example.poi.model.UmsMenu;
import com.example.poi.model.UmsResource;

import java.util.List;

/**
 * <p>
 * poi统一库 服务类
 * </p>
 *
 * @author test
 * @since 2022-09-03
 */
public interface PoiUnionService extends IService<PoiUnion> {
    /**
     * 添加poi
     */
    boolean create(PoiUnion poiUnion);

    /**
     * 批量添加poi
     */
    boolean createbatch(List<PoiUnion> poiUnionlist);

    /**
     * 批量添加poi batchsize
     */
    boolean createbatch(List<PoiUnion> poiUnionlist, int batchsize);
    /**
     * 修改poi
     */
    boolean update(Long id, PoiUnion poiUnion);

    /**
     * 删除poi
     */
    boolean delete(Long id);

    /**
     * 删除poi
     */
    boolean delete(List<Long> ids);

    /**
     * 分页查询poi
     */
    Page<PoiUnion> list(String displaynameKeyword, String addressKeyword, Long poi_id, Integer pageSize, Integer pageNum);
}
