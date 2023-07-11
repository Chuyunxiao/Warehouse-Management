package com.back.mapper;

import com.back.entity.Record;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage pageCC(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}