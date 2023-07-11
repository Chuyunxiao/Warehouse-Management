package com.back.controller;

import com.back.common.QueryPageParam;
import com.back.common.Result;
import com.back.entity.Goods;
import com.back.entity.Record;
import com.back.service.GoodsService;
import com.back.service.RecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String goodstype = (String)param.get("goodstype");
        String storage = (String)param.get("storage");
        String roleId = (String)param.get("roleId");
        String userId = (String)param.get("userId");

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
//      queryWrapper会在后面自动加where语句，所以我们使用apply()方法手动添加where后的语句
        queryWrapper.apply(" a.goods=b.id and b.storage=c.id and b.goodsType=d.id ");
        if("2".equals(roleId)) {
            queryWrapper.apply("a.userId= " + userId);
        }
        //      如果name不为空字符串'' 且不为null
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            queryWrapper.eq("d.id",goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.eq("c.id",storage);
        }
        IPage res = recordService.pageCC(page, queryWrapper);
        return Result.succ(res.getRecords(),res.getTotal());
    }
//  新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record) {
        Goods goods = goodsService.getById(record.getGoods());
//      获取原数目num,之后做更改
        int num = goods.getCount();
        int n = record.getCount();
//      入库
//      若为出库
        if("2".equals(record.getAction())) {
            n = -n;
            record.setCount(n);
        }
//      更新后的总数目num设置返回后端
        num += n;
        goods.setCount(num);
        goodsService.updateById(goods);
        return recordService.save(record) ? Result.succ() : Result.fail();
    }
}
