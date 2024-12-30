package org.jeecg.modules.bugdetect.regions.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.bugdetect.regions.entity.Regions;
import org.jeecg.modules.bugdetect.regions.service.IRegionsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 区域管理
 * @Author: jeecg-boot
 * @Date:   2024-12-20
 * @Version: V1.0
 */
@Api(tags="区域管理")
@RestController
@RequestMapping("/regions/regions")
@Slf4j
public class RegionsController extends JeecgController<Regions, IRegionsService> {
	@Autowired
	private IRegionsService regionsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param regions
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "区域管理-分页列表查询")
	@ApiOperation(value="区域管理-分页列表查询", notes="区域管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Regions>> queryPageList(Regions regions,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Regions> queryWrapper = QueryGenerator.initQueryWrapper(regions, req.getParameterMap());
		Page<Regions> page = new Page<Regions>(pageNo, pageSize);
		IPage<Regions> pageList = regionsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param regions
	 * @return
	 */
	@AutoLog(value = "区域管理-添加")
	@ApiOperation(value="区域管理-添加", notes="区域管理-添加")
	@RequiresPermissions("regions:regions:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Regions regions) {
		regionsService.save(regions);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param regions
	 * @return
	 */
	@AutoLog(value = "区域管理-编辑")
	@ApiOperation(value="区域管理-编辑", notes="区域管理-编辑")
	@RequiresPermissions("regions:regions:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Regions regions) {
		regionsService.updateById(regions);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "区域管理-通过id删除")
	@ApiOperation(value="区域管理-通过id删除", notes="区域管理-通过id删除")
	@RequiresPermissions("regions:regions:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		regionsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "区域管理-批量删除")
	@ApiOperation(value="区域管理-批量删除", notes="区域管理-批量删除")
	@RequiresPermissions("regions:regions:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.regionsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "区域管理-通过id查询")
	@ApiOperation(value="区域管理-通过id查询", notes="区域管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Regions> queryById(@RequestParam(name="id",required=true) String id) {
		Regions regions = regionsService.getById(id);
		if(regions==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(regions);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param regions
    */
    @RequiresPermissions("regions:regions:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Regions regions) {
        return super.exportXls(request, regions, Regions.class, "区域管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("regions:regions:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Regions.class);
    }

}
