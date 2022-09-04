package com.example.poi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.poi.model.UmsAdminRoleRelation;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsAdminRoleRelationMapper extends BaseMapper<UmsAdminRoleRelation> {
    List<Long> getAdminIdListBySrc(@Param("src") Integer src);

    Integer getSrcByAdminId(@Param("adminId") Long adminId);

}
