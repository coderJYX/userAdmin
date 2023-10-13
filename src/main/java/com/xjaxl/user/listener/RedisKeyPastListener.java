package com.xjaxl.user.listener;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xjaxl.user.common.consts.GlobalVariables;
import com.xjaxl.user.common.utils.ManaConstant;
import com.xjaxl.user.entity.dto.user.SysUserDTO;
import com.xjaxl.user.entity.vo.user.LoginVO;
import com.xjaxl.user.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.aspectj.apache.bcel.generic.ObjectType;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RedisKeyPastListener extends KeyExpirationEventMessageListener {

    @Resource
    private UserService userService;


    public RedisKeyPastListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // message.toString()可以获取失效的key
        String expiredKey = message.toString();

        if (expiredKey.startsWith("userId:")){
            String userName = expiredKey.substring(7);
            SysUserDTO sysUserDTO = userService.getOne(
                    new LambdaQueryWrapper<SysUserDTO>()
                            .eq(SysUserDTO::getUsername, userName)
                            .eq(SysUserDTO::getDelFlag, ManaConstant.Number.ZERO)
            );
            if (ObjectUtil.isEmpty(sysUserDTO)) {
                return;
            }
            sysUserDTO.setOnlineType(ManaConstant.StringNumber.ZERO);
            userService.updateById(sysUserDTO);
        }
    }
}
