package com.sinosoft.aod.feed.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author LongLei
 * @date   ${datetime}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("crm_service_config")
public class ServiceConfig extends Model<ServiceConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 服务ID
     */
	private String serviceId;
    /**
     * 服务名称
     */
	private String serviceName;
    /**
     * 服务简介
     */
	private String remark;
	/**
	 * 服务状态
	 */
	@TableField(value = "status_")
	private String status;
    /**
     * 创建人ID
     */
	private Long createId;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 修改人ID
     */
	private Long updateId;
    /**
     * 修改时间
     */
	private Date updateTime;
    /**
     * 是否删除
     */
	private String deleteFlag;

	/**
	 * 获取创建时间并格式化
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取修改时间并格式化
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
