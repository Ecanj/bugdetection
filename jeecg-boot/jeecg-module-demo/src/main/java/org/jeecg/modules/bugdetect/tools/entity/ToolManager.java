package org.jeecg.modules.bugdetect.tools.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 设备管理
 * @Author: jeecg-boot
 * @Date:   2024-11-22
 * @Version: V1.0
 */
@Data
@TableName("tool_manager")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tool_manager对象", description="设备管理")
public class ToolManager implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**设备序列号*/
	@Excel(name = "设备序列号", width = 15)
    @ApiModelProperty(value = "设备序列号")
    private java.lang.String deviceId;
	/**当前烟虫数量*/
	@Excel(name = "当前烟虫数量", width = 15)
    @ApiModelProperty(value = "当前烟虫数量")
    private java.lang.Integer bugNumber;
	/**设备位置*/
	@Excel(name = "设备位置", width = 15)
    @ApiModelProperty(value = "设备位置")
    private java.lang.String devicePosition;
	/**设备电量*/
	@Excel(name = "设备电量", width = 15)
    @ApiModelProperty(value = "设备电量")
    private java.lang.Integer deviceElectricity;
	/**设备信号*/
	@Excel(name = "设备信号", width = 15)
    @ApiModelProperty(value = "设备信号")
    private java.lang.String deviceSignal;
	/**剩余换纸*/
	@Excel(name = "剩余换纸", width = 15)
    @ApiModelProperty(value = "剩余换纸")
    private java.lang.Integer repaper;
	/**剩余天数*/
	@Excel(name = "剩余天数", width = 15)
    @ApiModelProperty(value = "剩余天数")
    private java.lang.Integer redate;
}
