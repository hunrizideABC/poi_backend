package com.example.poi.common.service;


import com.example.poi.common.elasticsearch.PoiUnionES;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 搜索商品管理Service
 * Created by macro on 2018/6/19.
 */
public interface PoiUnionESService {

    int importAll();

    void delete(Long id);

    PoiUnionES create(Long id);

    void delete(List<Long> ids);

    long count();

    public Page<PoiUnionES> search(String keyword, Integer pageNum, Integer pageSize);
}
