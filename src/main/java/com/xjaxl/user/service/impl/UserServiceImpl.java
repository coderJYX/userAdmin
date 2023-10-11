package com.xjaxl.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjaxl.user.common.consts.CodeMiscHeadType;
import com.xjaxl.user.common.consts.GlobalVariables;
import com.xjaxl.user.common.exceptions.BizException;
import com.xjaxl.user.common.utils.*;
import com.xjaxl.user.entity.dto.user.SysUserDTO;
import com.xjaxl.user.entity.rvo.user.UserRVO;
import com.xjaxl.user.entity.vo.user.LoginVO;
import com.xjaxl.user.entity.vo.user.UserSelectVO;
import com.xjaxl.user.entity.vo.user.UserVO;
import com.xjaxl.user.mapper.UserMapper;
import com.xjaxl.user.service.UserService;
import com.xjaxl.user.common.redis.RedisUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUserDTO>
        implements UserService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRVO login(LoginVO vo) {
        SysUserDTO sysUserDTO = getUserByUserName(vo.getUsername());

        if (ObjectUtil.isEmpty(sysUserDTO)) {
            throw new BizException("账号不存在");
        }

        String salt = sysUserDTO.getSalt();
        String userImportPassword = ShiroUtils.sha256(vo.getPassword(), salt);

        if (!Objects.equals(userImportPassword, sysUserDTO.getPassword())) {
            throw new BizException("密码不正确");
        }
        //获得token
        String token = TokenGenerator.generateValue();
        this.update(
                new LambdaUpdateWrapper<SysUserDTO>()
                        .eq(SysUserDTO::getId, sysUserDTO.getId())
                        .set(SysUserDTO::getOnlineType, ManaConstant.StringNumber.ONE)
        );

        redisUtil.set("token:" + token, sysUserDTO.getUsername(), 30 * 60);
        redisUtil.set("userId:" + sysUserDTO.getUsername(), sysUserDTO.getId(), 30 * 60);

        return UserRVO.builder()
                .userType(sysUserDTO.getUserType())
                .username(sysUserDTO.getUsername())
                .realName(sysUserDTO.getRealName())
                .unit(sysUserDTO.getUnit())
                .role(sysUserDTO.getRole())
                .gender(sysUserDTO.getGender())
                .onlineType(sysUserDTO.getOnlineType())
                .token(token)
                .build();
    }

    /**
     * 根据用户名获得用户信息
     *
     * @return {@link SysUserDTO }
     */
    public SysUserDTO getUserByUserName(String username) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<SysUserDTO>()
                        .eq(SysUserDTO::getUsername, username)
        );
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(UserVO vo) {
        //校验格式
        checkStatus(vo.getUserType(), vo.getGender());

        SysUserDTO sysUserDTO = getUserByUserName(vo.getUsername());
        if (ObjectUtil.isNotEmpty(sysUserDTO)) {
            throw new BizException("用户名已经存在");
        }
        SysUserDTO dto = BeanUtil.copyProperties(vo, SysUserDTO.class);
        EntityUtils.setBaseEntity(dto, CodeMiscHeadType.SYS_USER);

        //盐
        String salt = RandomStringUtils.randomAlphanumeric(20);
        dto.setSalt(salt);
        //加密密码
        dto.setPassword(ShiroUtils.sha256(dto.getPassword(), dto.getSalt()));

        return this.save(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SysUserDTO vo) {
        //校验格式
        checkStatus(vo.getUserType(), vo.getGender());

        SysUserDTO sysUserDTO = this.getById(vo.getId());
        EntityUtils.setBaseEntity(vo, CodeMiscHeadType.SYS_USER);

        String userImportPassword = ShiroUtils.sha256(vo.getPassword(), vo.getSalt());

        if (Objects.equals(userImportPassword, sysUserDTO.getPassword())) {
            vo.setPassword(userImportPassword);
            return this.updateById(vo);
        }

        //盐
        String salt = RandomStringUtils.randomAlphanumeric(20);
        vo.setSalt(salt);
        //加密密码
        vo.setPassword(ShiroUtils.sha256(vo.getPassword(), vo.getSalt()));

        return this.updateById(vo);
    }

    @Override
    public PageInfo<SysUserDTO> getList(UserSelectVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysUserDTO> list = this.list(
                new LambdaQueryWrapper<SysUserDTO>()
                        .eq(StringUtils.isNotBlank(vo.getUnit()), SysUserDTO::getUnit, vo.getUnit())
                        .like(StringUtils.isNotBlank(vo.getRealName()), SysUserDTO::getRealName, vo.getRealName())
                        .eq(SysUserDTO::getDelFlag, ManaConstant.Number.ZERO)
                        .orderByDesc(SysUserDTO::getCreateTime)
        );
        list.forEach(x -> {
            x.setUserType(StringUtils.equals(x.getUserType(), ManaConstant.StringNumber.ZERO) ? "管理员" : "普通用户");
            x.setOnlineType(StringUtils.equals(x.getOnlineType(), ManaConstant.StringNumber.ZERO) ? "离线" : "在线");
            x.setGender(StringUtils.equals(x.getGender(), ManaConstant.StringNumber.ONE) ? "男" :
                    StringUtils.equals(x.getGender(), ManaConstant.StringNumber.TWO) ? "女" : "未知");
        });

        return new PageInfo<>(list);
    }

    @Override
    public List<String> getUnitList() {
        return Optional.ofNullable(this.list())
                .orElse(Collections.emptyList())
                .stream()
                .map(SysUserDTO::getUnit)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String loginOut(String token) {
        this.update(
                new LambdaUpdateWrapper<SysUserDTO>()
                        .eq(SysUserDTO::getId, ShiroUtils.getUserId())
                        .set(SysUserDTO::getOnlineType, ManaConstant.StringNumber.ZERO)
        );
        redisUtil.delete("userId:" + ShiroUtils.getUsername());
        redisUtil.delete("token:" + token);
        return "退出成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(String id) {
        return this.update(
                new LambdaUpdateWrapper<SysUserDTO>()
                        .eq(SysUserDTO::getId, id)
                        .set(SysUserDTO::getDelFlag, ManaConstant.Number.ONE)
        );
    }

    @Override
    public UserRVO getDetail(String id) {
        SysUserDTO sysUserDTO = this.getById(id);
        if (ObjectUtil.isEmpty(sysUserDTO)) {
            throw new BizException("当前用户不存在");
        }
        return BeanUtil.copyProperties(sysUserDTO, UserRVO.class);
    }


    public void checkStatus(String userType, String gender) {
        if (!GlobalVariables.STATUS_ZERO_ONE.contains(userType)) {
            throw new BizException("用户类型格式错误");
        }
        if (!GlobalVariables.STATUS_ONE_TWO_THREE.contains(gender)) {
            throw new BizException("用户性别格式错误");
        }
    }


}




