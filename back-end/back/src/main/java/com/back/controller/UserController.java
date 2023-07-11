package com.back.controller;

import com.back.common.QueryPageParam;
import com.back.common.Result;
import com.back.entity.Menu;
import com.back.entity.User;
import com.back.service.MenuService;
import com.back.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
//  demo方法
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.succ(list) : Result.fail();
    }
//    新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.save(user)? Result.succ() : Result.fail();
    }
//    更新
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user)? Result.succ() : Result.fail();
    }
//    删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return userService.removeById(id)? Result.succ() : Result.fail();
    }
//    修改
    @PostMapping("/mod")
    public Boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }
//    登录
    @PostMapping("/login")
    public Result login (@RequestBody User user) {
        List list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()).list();

        if(list.size() > 0) {
            User user1 = (User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.succ(res);
        }
        return Result.fail();
    }
//    新增或修改
    @PostMapping("/saveOrMod")
    public Boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }
//    删除
    @GetMapping("/delete")
    public Boolean delete(Integer id) {
        return userService.removeById(id);
    }
//    查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(user.getName())){
            // 模糊查询
            lambdaQueryWrapper.like(User::getName, user.getName());
            // 精确查询
            //lambdaQueryWrapper.eq(User::getName, user.getName());
        }
        return Result.succ(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
//    public List<User> listPage(@RequestBody HashMap map){
    public List<User> listPage(@RequestBody QueryPageParam query){
//        System.out.println("pageNum = "+query.getPageNum());
//        System.out.println("pageSize = "+query.getPageSize());
        HashMap param = query.getParam();
        String name = (String)param.get("name");
//        System.out.println("name = "+(String)param.get("name"));
//        System.out.println("age = "+ param.get("age"));
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);

        IPage res = userService.page(page, lambdaQueryWrapper);
        System.out.println("total = "+res.getTotal());
        return res.getRecords();
    }
//  C 代表 "Customized" 用于区分普通查询
    @PostMapping("/listPageC")
//    public List<User> listPage(@RequestBody HashMap map){
    public List<User> listPageC(@RequestBody QueryPageParam query){
//        System.out.println("pageNum = "+query.getPageNum());
//        System.out.println("pageSize = "+query.getPageSize());
        HashMap param = query.getParam();
        String name = (String)param.get("name");
//        System.out.println("name = "+(String)param.get("name"));
//        System.out.println("age = "+ param.get("age"));
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);

//      CC 代表 "Customized Conditions"即 “自定义条件”，用于区分普通查询与pageC查询
        IPage res = userService.pageCC(page, lambdaQueryWrapper);
        System.out.println("total = "+res.getTotal());
        return res.getRecords();
    }

    @PostMapping("/listPageC1")
//    public List<User> listPage(@RequestBody HashMap map){
    public Result listPageC1(@RequestBody QueryPageParam query){
//        System.out.println("pageNum = "+query.getPageNum());
//        System.out.println("pageSize = "+query.getPageSize());
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String sex = (String)param.get("sex");
        String roleId = (String)param.get("roleId");
//        System.out.println("name = "+(String)param.get("name"));
//        System.out.println("age = "+ param.get("age"));
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
//      如果name不为空字符串'' 且不为null
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName, name);
        }
        if(StringUtils.isNotBlank(sex)){
//          查询性别 参数只有0/1 所以这里的LambdaQueryWrapper 用eq不用like
            lambdaQueryWrapper.eq(User::getSex, sex);
        }
        if(StringUtils.isNotBlank(roleId)){
//          查询管理员权限
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }
//        IPage res = userService.pageC(page);
        IPage res = userService.pageCC(page, lambdaQueryWrapper);
        System.out.println("total = "+res.getTotal());
        return Result.succ(res.getRecords(),res.getTotal());
    }
}
