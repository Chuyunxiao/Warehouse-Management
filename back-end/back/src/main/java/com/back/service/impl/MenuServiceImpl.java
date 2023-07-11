package com.back.service.impl;

import com.back.entity.Menu;
import com.back.mapper.MenuMapper;
import com.back.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
