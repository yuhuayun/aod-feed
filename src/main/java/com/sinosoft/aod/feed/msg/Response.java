package com.sinosoft.aod.feed.msg;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共返回值对象
 * Created by LONGLEI on 2018/1/23.
 */
@Data
@JsonAutoDetect
public class Response<T> implements Serializable {

    /**
     * 返回代码，0为成功，其他为调用错误
     */
    private int errCode;

    /**
     * 调用错误的时候，展示给用户的可读错误信息
     */
    private String errMsg;

    /**
     * 调用结果，JSON 的字符串
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

    public Response success() {
        this.errCode = 0;
        this.errMsg = "ok";
        return this;
    }

    public Response success(T data) {
        this.success();
        this.data = data;
        return this;
    }

    public Response failure(int errCode,String message) {
        this.errCode = errCode;
        this.errMsg = message;
        return this;
    }
}
