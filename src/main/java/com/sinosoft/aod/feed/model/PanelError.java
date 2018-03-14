package com.sinosoft.aod.feed.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("crm_panel_error")
public class PanelError extends Model<PanelError> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 案件主键ID
     */
	private Long caseId;
    /**
     * 错误行号
     */
	private Long errorLine;
	/**
	 * 异常类别 1-错误号码 2-文件异常
	 */
	private String abnormalType;
    /**
     * 错误类型
     */
	private String errorType;
    /**
     * 错误信息
     */
	private String errorInfo;
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


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
