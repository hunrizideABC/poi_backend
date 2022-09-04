package com.example.poi.mapper;

import com.example.poi.common.elasticsearch.PoiUnionES;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoiUnionESMapper {
    List<PoiUnionES> getAllPoiUnionESList();

    PoiUnionES getSinglePoiUnionESList(@Param("id") Long id);
}
