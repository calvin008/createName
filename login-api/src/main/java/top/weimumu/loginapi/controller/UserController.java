package top.weimumu.loginapi.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.weimumu.loginapi.common.GlobalResult;
import top.weimumu.loginapi.common.WechatUtil;
import top.weimumu.loginapi.dao.UserMapper;
import top.weimumu.loginapi.entity.User;

import java.util.Date;
import java.util.UUID;

/**
 * @author: create by calvin wong
 * @date:2019/12/4
 **/
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/wxlogin")
    public GlobalResult wxLogin(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "rawData", required = false) String rawData
    ) {
        JSONObject rawDataJson = JSON.parseObject(rawData);
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");
        User user = this.userMapper.selectById(openid);
        String skey = UUID.randomUUID().toString();
        if (user == null) {
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            user = new User();
            user.setOpenId(openid);
            user.setSkey(skey);
            user.setCreateTime(new Date());
            user.setLastVisitTime(new Date());
            user.setSession_key(sessionKey);
            user.setAvatarUrl(avatarUrl);
            user.setNickName(nickName);
            this.userMapper.insert(user);
        }else {
            // 已存在，更新用户登录时间

            user.setLastVisitTime(new Date());
//            // 重新设置会话skey
            user.setSkey(skey);

            this.userMapper.updateById(user);
        }
        GlobalResult result = GlobalResult.build(200, null, skey);
        return result;
    }
}
