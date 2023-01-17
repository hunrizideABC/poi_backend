package com.example.poi.controller;

import com.example.poi.common.api.CommonPage;
import com.example.poi.common.api.CommonResult;
import com.example.poi.common.elasticsearch.PoiUnionES;
import com.example.poi.common.service.PoiUnionESService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "poiSearch")
@Tag(name = "poiSearch",description = "poi搜索管理")
@RequestMapping("/poiSearch")
public class PoiSearchController {
    @Autowired
    private PoiUnionESService poiUnionESService;

    @ApiOperation(value = "根据id删除poi")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable Long id) {
        poiUnionESService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id批量删除poi")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids) {
        poiUnionESService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建poi")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<PoiUnionES> create(@PathVariable Long id) {
        PoiUnionES esPoiUnion = poiUnionESService.create(id);
        if (esPoiUnion != null) {
            return CommonResult.success(esPoiUnion);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "综合搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PoiUnionES>> search(@RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<PoiUnionES> esPoiUnionPage = poiUnionESService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esPoiUnionPage));
    }
}
