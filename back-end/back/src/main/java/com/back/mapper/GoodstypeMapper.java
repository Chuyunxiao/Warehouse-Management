package com.back.mapper;

import com.back.entity.Goodstype;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodstypeMapper extends BaseMapper<Goodstype> {
    IPage pageCC(IPage<Goodstype> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
