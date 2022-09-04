package com.example.poi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.poi.common.api.CommonPage;
import com.example.poi.common.api.CommonResult;
import com.example.poi.model.PoiCheck;
import com.example.poi.model.PoiUnion;
import com.example.poi.service.PoiCheckService;
import com.example.poi.service.PoiUnionService;
import com.example.poi.service.UmsAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * poi审核表 前端控制器
 * </p>
 *
 * @author test
 * @since 2022-09-03
 */
@RestController
@RequestMapping("/poiCheck")
public class PoiCheckController {
    @Autowired
    private PoiCheckService poiCheckService;

    @Autowired
    private PoiUnionService poiUnionService;

    @Autowired
    private UmsAdminService adminService;

    @ApiOperation("根据地址或名称分页获取poi列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PoiCheck>> list(@RequestParam(value = "status", defaultValue = "0") Integer status,
                                                   @RequestParam(value = "username", required = false) String username,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
                                                   ) {
        Long adminId = adminService.getCacheService().getAdmin(username).getId();
        System.out.println(adminId);
        Page<PoiCheck> poiCheckList = poiCheckService.list(adminId,status, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(poiCheckList));
    }

    @ApiOperation("修改审核poi信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody PoiCheck poiCheck) {
        boolean success_1 = poiCheckService.update(id, poiCheck);
        PoiUnion poiUnion = new PoiUnion();
        poiUnion.setLng(poiCheck.getLng());
        poiUnion.setLat(poiCheck.getLat());
        poiUnion.setAddress(poiCheck.getAddress());
        poiUnion.setDisplayname(poiCheck.getDisplayname());
        poiUnion.setProvince(poiCheck.getProvince());
        poiUnion.setCity(poiCheck.getCity());
        poiUnion.setCounty(poiCheck.getCounty());
        boolean success_2 = poiUnionService.update(poiCheck.getPoiId(),poiUnion);
        if (success_1 && success_2) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("下线指定poi信息")
    @RequestMapping(value = "/offline/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult offline(@PathVariable Long id, @RequestParam(value = "poi_id") Long poi_id) {
        PoiUnion poiUnion = new PoiUnion();
        poiUnion.setStatus(0);
        boolean success_1 = poiUnionService.update(poi_id,poiUnion);

        PoiCheck poiCheck = new PoiCheck();
        poiCheck.setStatus(1);
        poiCheck.setCheckStatus(2);
        poiCheck.setCheckDesc("下线");
        boolean success_2 = poiCheckService.update(id,poiCheck);
        if (success_1 && success_2) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改审核poi状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        PoiCheck poiCheck = new PoiCheck();
        poiCheck.setStatus(status);
        boolean success = poiCheckService.update(id,poiCheck);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}

