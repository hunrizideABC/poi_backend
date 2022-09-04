package com.example.poi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台用户登录日志表
 * </p>
 *
 * @author macro
 * @since 2022-08-26
 */
@Getter
@Setter
@TableName("ums_admin_login_log")
@ApiModel(value = "UmsAdminLoginLog对象", description = "后台用户登录日志表")
public class UmsAdminLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long adminId;

    private Date createTime;

    private String ip;

    private String address;

    @ApiModelProperty("浏览器登录类型")
    private String userAgent;


}
