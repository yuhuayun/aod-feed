package com.sinosoft.aod.feed.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LongLei
 * @date
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("crm_panel")
public class Panel extends Model<Panel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 名单案件id
     */
	private Long caseId;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 客户ID
	 */
	private String custId;
	/**
	 * 优先级
	 */
	private String priority;
	/**
	 *号码1
	 */
	private String phone1;
	/**
	 * 号码2
	 */
	private String phone2;
	/**
	 * 号码3
	 */
	private String phone3;
	/**
	 * 号码4
	 */
	private String phone4;
	/**
	 * 号码5
	 */
	private String phone5;
	/**
	 * 号码6
	 */
	private String phone6;
    /**
     * 备用字段1
     */
	private Long reserve1;
    /**
     * 备用字段2
     */
	private Long reserve2;
    /**
     * 备用字段3
     */
	private Long reserve3;
    /**
     * 备用字段4
     */
	private Long reserve4;
    /**
     * 备用字段5
     */
	private Long reserve5;
    /**
     * 备用字段6
     */
	private Double reserve6;
    /**
     * 备用字段7
     */
	private Double reserve7;
    /**
     * 备用字段8
     */
	private Double reserve8;
    /**
     * 备用字段9
     */
	private Double reserve9;
    /**
     * 备用字段10
     */
	private Double reserve10;
    /**
     * 备用字段11
     */
	private Date reserve11;
    /**
     * 备用字段12
     */
	private Date reserve12;
    /**
     * 备用字段13
     */
	private Date reserve13;
    /**
     * 备用字段14
     */
	private Date reserve14;
    /**
     * 备用字段15
     */
	private Date reserve15;
    /**
     * 备用字段16
     */
	private String reserve16;
    /**
     * 备用字段17
     */
	private String reserve17;
    /**
     * 备用字段18
     */
	private String reserve18;
    /**
     * 备用字段19
     */
	private String reserve19;
    /**
     * 备用字段20
     */
	private String reserve20;
    /**
     * 备用字段21
     */
	private String reserve21;
    /**
     * 备用字段22
     */
	private String reserve22;
    /**
     * 备用字段23
     */
	private String reserve23;
    /**
     * 备用字段24
     */
	private String reserve24;
    /**
     * 备用字段25
     */
	private String reserve25;
    /**
     * 备用字段26
     */
	private String reserve26;
    /**
     * 备用字段27
     */
	private String reserve27;
    /**
     * 备用字段28
     */
	private String reserve28;
    /**
     * 备用字段29
     */
	private String reserve29;
    /**
     * 备用字段30
     */
	private String reserve30;
    /**
     * 备用字段31
     */
	private String reserve31;
    /**
     * 备用字段32
     */
	private String reserve32;
    /**
     * 备用字段33
     */
	private String reserve33;
    /**
     * 备用字段34
     */
	private String reserve34;
    /**
     * 备用字段35
     */
	private String reserve35;
	/**
	 * 状态
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
	 * 上传数量
	 */
	@TableField(exist = false)
	private Integer feednumber;
	/**
	 * 服务ID
	 */
	@TableField(exist = false)
	private String serviceId;

	/**
	 * 重新上传的名单ID
	 */
	@TableField(exist = false)
	private List panelIds;
	/**
	 * 剔除的号码
	 */
	@TableField(exist = false)
	private String phone;

	/**
	 * 剔除的名单路径
	 */
	@TableField(exist = false)
	private String filePath;

	/**
	 *文件名称
	 */
	@TableField(exist = false)
	private String fileName;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
