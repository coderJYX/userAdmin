package com.xjaxl.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xjaxl.user.entity.dto.user.SysUserDTO;
import com.xjaxl.user.entity.rvo.user.UserRVO;
import com.xjaxl.user.entity.vo.user.LoginVO;
import com.xjaxl.user.entity.vo.user.UserSelectVO;
import com.xjaxl.user.entity.vo.user.UserVO;

import java.util.List;


public interface UserService extends IService<SysUserDTO> {

    UserRVO login(LoginVO vo);

    boolean addUser(UserVO vo);

    boolean updateUser(SysUserDTO vo);

    PageInfo<SysUserDTO> getList(UserSelectVO vo);

    List<String> getUnitList();

    String loginOut(String token);

    boolean deleteById(String id);

    UserRVO getDetail(String id);

}
