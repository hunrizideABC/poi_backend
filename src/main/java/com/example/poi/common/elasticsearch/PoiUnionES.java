package com.example.poi.common.elasticsearch;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode
@Document(indexName = "poi_union")
@Setting(shards = 1,replicas = 0)
public class PoiUnionES implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Long id;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String displayname;

    @Field(type = FieldType.Keyword)
    private String categoryLevel1;

    @Field(type = FieldType.Keyword)
    private String categoryLevel2;

    @Field(type = FieldType.Keyword)
    private String categoryLevel3;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Keyword)
    private String province;

    @Field(type = FieldType.Keyword)
    private String city;

    @Field(type = FieldType.Keyword)
    private String county;

    @Field(type = FieldType.Double)
    private BigDecimal lng;

    @Field(type = FieldType.Double)
    private BigDecimal lat;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Integer)
    private Integer status;
}
