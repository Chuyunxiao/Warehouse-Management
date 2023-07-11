package com.back.controller;

import com.back.common.QueryPageParam;
import com.back.common.Result;
import com.back.entity.Storage;
import com.back.service.StorageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;
    //    新增
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage) {
        return storageService.save(storage)? Result.succ() : Result.fail();
    }
    //    更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage) {
        return storageService.updateById(storage)? Result.succ() : Result.fail();
    }
    //    删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return storageService.removeById(id)? Result.succ() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper();
//      如果name不为空字符串'' 且不为null
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Storage::getName, name);
        }

        IPage res = storageService.pageCC(page, lambdaQueryWrapper);
        return Result.succ(res.getRecords(),res.getTotal());
    }

    @GetMapping("/list")
    public Result list() {
        List list = storageService.list();
        return Result.succ(list);
    }
}
