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
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author LongLei
 * @date ${datetime}
 */
@Data
public class Monitor  {
    /**
     * 主键id
     */
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 名单总数
     */
    private Integer listSum;
    /**
     * 导入平台数
     */
    private Integer imPlatformNum;
    /**
     * 拨打数
     */
    private Integer dialNum;
    /**
     * 接通数
     */
    private Integer successNum;
    /**
     * 接通率
     */
    private String connectRate;
    /**
     * 状态:启动 1,停用 2
     */
    private String status;
    /**
     * 监控时间
     */
    private Date time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    /**
     * 求百分比
     *
     * @return 返回值格式为33.33%
     */

    public String getConnectRate() {
        if (getDialNum() != null && getDialNum().intValue() != 0 && getSuccessNum() != null) {
            DecimalFormat a = new DecimalFormat("#.00%");
            return a.format((float) successNum / dialNum);
        }
        return "0%";
    }
}