package org.jeecg.modules.bugdetect.toolsrecords.controller;

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
import org.jeecg.modules.bugdetect.toolsrecords.entity.ToolRecords;
import org.jeecg.modules.bugdetect.toolsrecords.service.IToolRecordsService;

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
 * @Description: 设备记录
 * @Author: jeecg-boot
 * @Date:   2024-11-22
 * @Version: V1.0
 */
@Api(tags="设备记录")
@RestController
@RequestMapping("/toolsrecords/toolRecords")
@Slf4j
public class ToolRecordsController extends JeecgController<ToolRecords, IToolRecordsService> {
	@Autowired
	private IToolRecordsService toolRecordsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param toolRecords
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "设备记录-分页列表查询")
	@ApiOperation(value="设备记录-分页列表查询", notes="设备记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ToolRecords>> queryPageList(ToolRecords toolRecords,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<ToolRecords> queryWrapper = QueryGenerator.initQueryWrapper(toolRecords, req.getParameterMap());
		Page<ToolRecords> page = new Page<ToolRecords>(pageNo, pageSize);
		IPage<ToolRecords> pageList = toolRecordsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param toolRecords
	 * @return
	 */
	@AutoLog(value = "设备记录-添加")
	@ApiOperation(value="设备记录-添加", notes="设备记录-添加")
	@RequiresPermissions("toolsrecords:tool_records:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ToolRecords toolRecords) {
		toolRecordsService.save(toolRecords);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param toolRecords
	 * @return
	 */
	@AutoLog(value = "设备记录-编辑")
	@ApiOperation(value="设备记录-编辑", notes="设备记录-编辑")
	@RequiresPermissions("toolsrecords:tool_records:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ToolRecords toolRecords) {
		toolRecordsService.updateById(toolRecords);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备记录-通过id删除")
	@ApiOperation(value="设备记录-通过id删除", notes="设备记录-通过id删除")
	@RequiresPermissions("toolsrecords:tool_records:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		toolRecordsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "设备记录-批量删除")
	@ApiOperation(value="设备记录-批量删除", notes="设备记录-批量删除")
	@RequiresPermissions("toolsrecords:tool_records:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.toolRecordsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "设备记录-通过id查询")
	@ApiOperation(value="设备记录-通过id查询", notes="设备记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ToolRecords> queryById(@RequestParam(name="id",required=true) String id) {
		ToolRecords toolRecords = toolRecordsService.getById(id);
		if(toolRecords==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(toolRecords);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param toolRecords
    */
    @RequiresPermissions("toolsrecords:tool_records:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ToolRecords toolRecords) {
        return super.exportXls(request, toolRecords, ToolRecords.class, "设备记录");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("toolsrecords:tool_records:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ToolRecords.class);
    }

}
