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
 * 案件实体类
 * </p>
 *
 * @author LongLei
 * @date 2018-02-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("crm_case_info")
public class CaseInfo extends Model<CaseInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 文件名称
     */
    private String fileName;
    /**
     * 上传总数
     */
    private Long totalCount;
    /**
     * 成功数量
     */
    private Long successCount;
    /**
     * 案件状态
     */
    @TableField(value = "status_")
    private String status;
    /**
     * 备注
     */
    private String remark;
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
     * 上传文件路径
     */
    @TableField(exist = false)
    private String filePath;
    /**
     * 查询参数,作用于createTime
     */
    @TableField(exist = false)
    private String selectStartTime;
    /**
     * 查询参数,作用于createTime
     */
    @TableField(exist = false)
    private String selectEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
