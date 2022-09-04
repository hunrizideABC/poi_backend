package com.example.poi;


import com.example.poi.model.PoiUnion;
import com.example.poi.service.PoiUnionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Tests_union {
    @Autowired
    private PoiUnionService poiUnionService;


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

//    @Test
//    public void  testinsert()  {
//        String path = "D:\\IdeaProjects\\poi_backend\\poi_data\\data_bj.txt";
//        List<String> lists = readTxt(path);
//        List<PoiUnion> poiUnionList = new ArrayList<>();
//        for (String list:lists){
//            String[] split = list.split("\t");
//            if(split.length==10) {
//                PoiUnion poiUnion = new PoiUnion();
//                poiUnion.setDisplayname(split[0]);
//                poiUnion.setCategoryLevel1(split[1]);
//                poiUnion.setCategoryLevel2(split[2]);
//                poiUnion.setCategoryLevel3(split[3]);
//                poiUnion.setAddress(split[4]);
//                poiUnion.setProvince(split[5]);
//                poiUnion.setCity(split[6]);
//                poiUnion.setCounty(split[7]);
//                BigDecimal lng = new BigDecimal(split[8]);
//                lng = lng.setScale(5, BigDecimal.ROUND_HALF_UP);
//                poiUnion.setLng(lng);
//                BigDecimal lat = new BigDecimal(split[9]);
//                lat = lat.setScale(5, BigDecimal.ROUND_HALF_UP);
//                poiUnion.setLat(lat);
//                poiUnion.setStatus(1);
//                poiUnionList.add(poiUnion);
//            }
//        }
//        poiUnionService.createbatch(poiUnionList);
//    }

    @Test
    public void  testquery()  {
        Page<PoiUnion> poiList= poiUnionService.list("","",87l,10,1);
        List<PoiUnion> test = poiList.getRecords();
        for(PoiUnion poiUnion:test){
            System.out.println(poiUnion.getDisplayname());
            System.out.println(poiUnion.getAddress());
        }

    }

}
