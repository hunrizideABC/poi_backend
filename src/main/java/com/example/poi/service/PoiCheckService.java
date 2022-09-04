package com.example.poi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.poi.model.PoiCheck;

import java.util.List;


/**
 * <p>
 * poi审核表 服务类
 * </p>
 *
 * @author test
 * @since 2022-09-03
 */
public interface PoiCheckService extends IService<PoiCheck> {
    /**
     * 添加poi
     */
    boolean create(PoiCheck poiCheck);

    /**
     * 批量添加poi
     */
    boolean createbatch(List<PoiCheck> poiChecklist, int src);

    /**
     * 批量添加poi batchsize
     */
    boolean createbatch(List<PoiCheck> poiChecklist, int src, int batchsize);

    /**
     * 修改poi
     */
    boolean update(Long id, PoiCheck poiCheck);

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
    Page<PoiCheck> list(Long adminId, Integer src, Integer pageSize, Integer pageNum);
}
