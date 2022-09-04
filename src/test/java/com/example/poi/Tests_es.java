package com.example.poi;


import com.example.poi.common.elasticsearch.PoiUnionES;
import com.example.poi.common.elasticsearch.PoiUnionRepository;
import com.example.poi.common.service.PoiUnionESService;
import com.example.poi.mapper.PoiUnionESMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Tests_es {
//    @Autowired
//    private PoiUnionESMapper poiUnionESMapper;
//    @Test
//    public void  test()  {
//        PoiUnionES poiUnionES = poiUnionESMapper.getAllPoiUnionESList(100l).get(0);
//        System.out.println(poiUnionES.getAddress());
//    }

    @Autowired
    private PoiUnionESService poiUnionESService;


    @Test
    public void  test()  {
        Page<PoiUnionES> poiUnionESPage= poiUnionESService.search("上海财经大学",0,5);
        List<PoiUnionES> poiUnionESList = poiUnionESPage.getContent();
        for(PoiUnionES test:poiUnionESList){
            System.out.println(test.getDisplayname());
            System.out.println(test.getAddress());
        }
    }
}
