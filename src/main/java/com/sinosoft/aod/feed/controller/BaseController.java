package com.sinosoft.aod.feed.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sinosoft.aod.feed.msg.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 基础公共 BaseController
 *
 * @author Wang JunHua
 * @create   2017-11-15 8:48
 */
@Slf4j
public abstract class BaseController<Service extends IService,Entity>{

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected Service baseService;

    @PostMapping
    public Response add(@RequestBody @Valid Entity entity){
        log.debug("add()");
        baseService.insert(entity);
        return new Response().success();
    }

    @GetMapping(value = "/{id}")
    public Response get(@PathVariable long id){
        return new Response().success(baseService.selectById(id));
    }

    @PutMapping(value = "/{id}")
    public Response update(@RequestBody @Valid Entity entity){
        baseService.updateById(entity);
        return new Response().success();
    }

    @DeleteMapping(value = "/{id}")
    public Response delete(@PathVariable long id){
        baseService.deleteById(id);
        return new Response().success();
    }


    @GetMapping("/all")
    public List<Entity> all(){
        return baseService.selectList(null);
    }

    @GetMapping("/page")
    public Page<Entity> list(Page page,Entity entity){
        return baseService.selectPage(page,new EntityWrapper<>(entity));
    }

}
