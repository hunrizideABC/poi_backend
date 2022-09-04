package com.example.poi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.poi.common.api.CommonPage;
import com.example.poi.common.api.CommonResult;
import com.example.poi.model.PoiUnion;
import com.example.poi.model.UmsAdmin;
import com.example.poi.model.UmsMenu;
import com.example.poi.service.PoiUnionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * poi统一库 前端控制器
 * </p>
 *
 * @author test
 * @since 2022-09-03
 */
@Controller
@Api(tags = "poiUnion")
@Tag(name = "poiUnion",description = "poi统一库管理")
@RequestMapping("/poiUnion")
public class PoiUnionController {
    @Autowired
    private PoiUnionService poiUnionService;

    @ApiOperation("根据地址或名称分页获取poi列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PoiUnion>> list(@RequestParam(value = "displaynamekeyword", required = false) String displaynamekeyword,
                                                   @RequestParam(value = "addresskeyword", required = false) String addresskeyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "poi_id", defaultValue = "-1") Long poi_id) {
        Page<PoiUnion> poiUnionList = poiUnionService.list(displaynamekeyword,addresskeyword, poi_id, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(poiUnionList));
    }

    @ApiOperation("修改poi信息")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody PoiUnion poiUnion) {
        boolean success = poiUnionService.create(poiUnion);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("修改指定poi信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody PoiUnion poiUnion) {
        boolean success = poiUnionService.update(id, poiUnion);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除指定poi信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        boolean success = poiUnionService.delete(id);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改poi状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        PoiUnion poiUnion = new PoiUnion();
        poiUnion.setStatus(status);
        boolean success = poiUnionService.update(id,poiUnion);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

}

