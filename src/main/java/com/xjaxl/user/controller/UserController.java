package com.xjaxl.user.controller;


import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xjaxl.user.common.utils.SystemUtils;
import com.xjaxl.user.entity.dto.user.SysUserDTO;
import com.xjaxl.user.entity.rvo.user.UserRVO;
import com.xjaxl.user.entity.vo.user.LoginVO;
import com.xjaxl.user.entity.vo.user.UserSelectVO;
import com.xjaxl.user.entity.vo.user.UserVO;
import com.xjaxl.user.service.UserService;
import com.xjaxl.user.common.result.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResultWrapper login(@RequestBody LoginVO vo) {
        return ResultWrapper.success(userService.login(vo));
    }


    @PostMapping("/add")
    public ResultWrapper<Boolean> addUser(@RequestBody UserVO vo){
        //校验是否是管理员
        SystemUtils.checkPermissions();
        return ResultWrapper.success(userService.addUser(vo));
    }

    @PostMapping("/update")
    public ResultWrapper<Boolean> updateUser(@RequestBody SysUserDTO vo){
        //校验是否是管理员
        SystemUtils.checkPermissions();
        return ResultWrapper.success(userService.updateUser(vo));
    }

    @PostMapping("/list")
    public ResultWrapper<PageInfo<SysUserDTO>> getList(@RequestBody UserSelectVO vo){
        return ResultWrapper.success(userService.getList(vo));

    }

    @GetMapping("/unitList")
    public ResultWrapper<List<String>> getUnitList(){
        return ResultWrapper.success(userService.getUnitList());
    }


    @GetMapping("/loginOut")
    public ResultWrapper<String> loginOut(String token) {
        String success = userService.loginOut(token);
        return ResultWrapper.success(success);
    }

    @GetMapping("/deleteById")
    public ResultWrapper<Boolean> deleteById(String id) {
        return ResultWrapper.success(userService.deleteById(id));
    }

    @GetMapping("detail")
    public ResultWrapper<UserRVO> getDetail(String id) {
        return ResultWrapper.success(userService.getDetail(id));
    }

}
