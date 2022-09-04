package com.example.poi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * poi审核表
 * </p>
 *
 * @author test
 * @since 2022-09-04
 */
@Getter
@Setter
@TableName("poi_check")
@ApiModel(value = "PoiCheck对象", description = "poi审核表")
public class PoiCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long poiId;

    private Long adminId;

    @ApiModelProperty("名称")
    private String displayname;

    @ApiModelProperty("大类")
    private String categoryLevel1;

    @ApiModelProperty("中类")
    private String categoryLevel2;

    @ApiModelProperty("小类")
    private String categoryLevel3;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String county;

    @ApiModelProperty("经度")
    private BigDecimal lng;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("poi审核状态：0->不修改；1->修改；2->下线")
    private Integer checkStatus;

    @ApiModelProperty("审核描述")
    private String checkDesc;

    @ApiModelProperty("审核产线")
    private Integer src;

    @ApiModelProperty("检查状态：0->未检查；1->已检查")
    private Integer status;


}
