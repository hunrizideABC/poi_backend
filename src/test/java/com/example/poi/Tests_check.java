package com.example.poi;


import com.example.poi.mapper.UmsAdminRoleRelationMapper;
import com.example.poi.model.PoiCheck;
import com.example.poi.model.UmsAdminRoleRelation;
import com.example.poi.service.PoiCheckService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Tests_check {
    @Autowired
    private PoiCheckService poiCheckService;
    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    public List<String> readTxt(String path) {
        List<String> lists = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
            String lineTxt = null;
            int count = 0;
            // 逐行读取
            while ((lineTxt = br.readLine()) != null) {
                // 输出内容到控制台
                lists.add(lineTxt);
                count++;
            }
            br.close();
            System.out.println("count=" + count);
        } catch (Exception e) {
        }
        return lists;
    }

    @Test
    public void  testinsert()  {
//        String path = "D:\\IdeaProjects\\poi_backend\\poi_data\\data_check_abnormal.txt";
        String path = "D:\\IdeaProjects\\poi_backend\\poi_data\\data_check_yaw.txt";
        List<String> lists = readTxt(path);
        List<PoiCheck> poiCheckList = new ArrayList<>();
        for (String list:lists){
            String[] split = list.split("\t");
            if(split.length==11) {
                PoiCheck poiCheck = new PoiCheck();
                poiCheck.setPoiId(Long.parseLong(split[0]));
                poiCheck.setDisplayname(split[1]);
                poiCheck.setCategoryLevel1(split[2]);
                poiCheck.setCategoryLevel2(split[3]);
                poiCheck.setCategoryLevel3(split[4]);
                poiCheck.setAddress(split[5]);
                poiCheck.setProvince(split[6]);
                poiCheck.setCity(split[7]);
                poiCheck.setCounty(split[8]);
                BigDecimal lng = new BigDecimal(split[9]);
                lng = lng.setScale(5, BigDecimal.ROUND_HALF_UP);
                poiCheck.setLng(lng);
                BigDecimal lat = new BigDecimal(split[10]);
                lat = lat.setScale(5, BigDecimal.ROUND_HALF_UP);
                poiCheck.setLat(lat);
//                poiCheck.setSrc(2);
                poiCheck.setSrc(1);
                poiCheckList.add(poiCheck);
            }
        }
//        poiCheckService.createbatch(poiCheckList,2);
        poiCheckService.createbatch(poiCheckList,1);
    }

//    @Test
//    public void  testquery()  {
////        Page<PoiCheck> poiList= poiCheckService.list("","",1l,0,100,1);
////        List<PoiCheck> test = poiList.getRecords();
////        System.out.println(test.size());
////        for(PoiCheck poiUnion:test){
////            System.out.println(poiUnion.getDisplayname());
////            System.out.println(poiUnion.getAdminId());
////            System.out.println(poiUnion.getCreateTime());
////            System.out.println(poiUnion.getSrc());
////        }
//
//    }



}
