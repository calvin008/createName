# uni-app微信小程序登录

## 第一章 前端页面

### 1.1 简介

前端使用uni-app

后端springboot2.X+mybatis plus

持久化数据库mysql8.0.16

效果展示:

![image](https://github.com/calvin008/weixin-login/blob/master/image/show.gif)

### 1.2 微信小程序登录步骤

![image](https://github.com/calvin008/weixin-login/blob/master/image/login-api.jpg)

*第一步：小程序通过uni.login()获取code。*

*第二步：小程序通过uni.request()发送code到开发者服务器。*

*第三步：开发者服务器接收小程序发送的code，并携带appid、appsecret（这两个需要到微信小程序后台查看）、code发送到微信服务器。*

*第四步：微信服务器接收开发者服务器发送的appid、appsecret、code进行校验。校验通过后向开发者服务器发送session_key、openid。*

*第五步：开发者服务器自己生成一个key（自定义登录状态）与openid、session_key进行关联，并存到数据库中（mysql、redis等）。*

*第六步：开发者服务器返回生成key（自定义登录状态）到小程序。*

*第七步：小程序存储key（自定义登录状态）到本地。*



### 1.3首页index

```
<template>
	<view class="content">
		<image class="logo" :src="userInfo.avatarUrl || '/static/missing-face.png'"></image>
		<view class="text-area">
			<text class="title" @click="toLogin">{{ hasLogin ? userInfo.nickName || '未设置昵称' : '立即登录' }}</text>
		</view>
	</view>
</template>

<script>
import { mapState } from 'vuex';
export default {
	data() {
		return {};
	},
	onLoad() {},
	computed: {
		...mapState(['hasLogin', 'userInfo'])
	},

	methods: {
		toLogin() {
			if (!this.hasLogin) {
				uni.navigateTo({
					url: '/pages/login/login'
				});
			}
		}
	}
};
</script>

<style>
.content {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

.logo {
	height: 200rpx;
	width: 200rpx;
	margin-top: 200rpx;
	margin-left: auto;
	margin-right: auto;
	margin-bottom: 50rpx;
}

.text-area {
	display: flex;
	justify-content: center;
}

.title {
	font-size: 36rpx;
	color: #8f8f94;
}
</style>

```



### 1.4 登录页面login

```
<template>
	<view class="container">
		<view class="left-top-sign">LOGIN</view>
		<view class="welcome">欢迎回来！</view>
		<button class="confirm-btn" open-type="getUserInfo" @getuserinfo="wxLogin" :disabled="logining">微信授权登录</button>
	</view>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
	data() {
		return {
			logining: false
		};
	},
	onLoad() {},
	methods: {
		wxLogin(e) {
		const that = this;
		that.logining = true;
		let userInfo = e.detail.userInfo;
		uni.login({
			provider:"weixin",
			success:(login_res => {
				let code = login_res.code;
				uni.getUserInfo({
					success(info_res) {
						console.log(info_res)
						uni.request({
							url:'http://localhost:8080/wxlogin',
							method:"POST",
							header: {
							                  'content-type': 'application/x-www-form-urlencoded'
							                },
							data:{
								code : code,
								rawData : info_res.rawData
							},
							success(res) {
								if(res.data.status == 200){
									that.$store.commit('login',userInfo);
									// uni.setStorageSync("userInfo",userInfo);
									// uni.setStorageSync("skey", res.data.data);
								}else{
									console.log('服务器异常')
								}
							},
							fail(error) {
								console.log(error)
							}
						})
						uni.hideLoading()
						uni.navigateBack()
					}
				})
				
			})
			})
		}
	}
};
</script>

<style lang="scss">
.container {
	display: flex;
	overflow: hidden;
	background: #fff;
	flex-direction: column;
	justify-content: center;
	.left-top-sign {
		font-size: 120upx;
		color: $page-color-base;
		position: relative;
		left: -10upx;
		margin-top: 100upx;
	}
	.welcome {
		position: relative;
		left: 50upx;
		top: -90upx;
		font-size: 46upx;
		color: #555;
		text-shadow: 1px 0px 1px rgba(0, 0, 0, 0.3);
	}
	.confirm-btn {
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin-top: 70upx;
		background: $uni-color-primary;
		color: #fff;
		font-size: $font-lg;
		&:after {
			border-radius: 100px;
		}
	}
}
</style>

```



## 第二章 uni-app业务逻辑开发

### 2.1 vuex在uni-app中的使用

```
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		hasLogin: false,
		userInfo: {},
	},
	mutations: {
		login(state, provider) {

			state.hasLogin = true;
			state.userInfo = provider;
			uni.setStorage({//缓存用户登陆状态
			    key: 'userInfo',  
			    data: provider  
			}) 
			console.log(state.userInfo);
		},
		logout(state) {
			state.hasLogin = false;
			state.userInfo = {};
			uni.removeStorage({  
	            key: 'userInfo'  
	        })
		}
	},
	actions: {
	
	}

})

export default store
```



### 2.2 登录功能实现

小程序已经抛弃getUserInfo,使用open-type绑定即可;

调用 wx.login 获取 code。

使用 wx.getSetting 获取用户的授权情况

- 如果用户已经授权，直接调用 API wx.getUserInfo 获取用户最新的信息；
- 用户未授权，在界面中显示一个按钮提示用户登入，当用户点击并授权后就获取到用户的最新信息。

将获取到的用户数据连同wx.login返回的code一同传给后端





## 第三章 数据库设计与运行

### 3.1 vmware使用docker安装mysql

**docker安装mysql,远程访问**

```text
//搜索mysql
docker search mysql
//选定版本，抓取镜像
docker pull mysql:8.0
//创建同步mysql的文件夹
mkdir -p /data/mysql01
//创建容器
docker run --name mysql01  -p 3307:3306 -v /data/mysql01:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=zan123456 -d mysql:8.0 


--restart 标志会检查容器的退出代码，并据此来决定是否要重启容器，默认是不会重启。
--restart的参数说明
always：无论容器的退出代码是什么，Docker都会自动重启该容器。
on-failure：只有当容器的退出代码为非0值的时候才会自动重启。另外，该参数还接受一个可选的重启次数参数，`--restart=on-fialure:5`表示当容器退出代码为非0时，Docker会尝试自动重启该容器，最多5次。

-v 容器内的 /var/lib/mysql 在宿主机上 /data/mysql01 做映射  
-e MYSQL_ROOT_PASSWORD 初始密码
-p 将宿主机3306的端口映射到容器3306端口
```

**error:如果启动失败，查看日志docker logs mysql01提示**

chown: cannot read directory '/var/lib/mysql/': Permission denied

容器中没有执行权限 //挂载外部数据卷时,无法启动容器, 报 chown: cannot read directory '/var/lib/mysql/': Permission denied 由$ docker logs [name] 查看得知 该原因为centOs7默认开启selinux安全模块,需要临时关闭该安全模块,或者添加目录到白名单 临时关闭selinux：su -c "setenforce 0" 重新开启selinux：su -c "setenforce 1" 添加selinux规则，将要挂载的目录添加到白名单： 示例：chcon -Rt svirt_sandbox_file_t /data/mysql01(我启动挂载的路径)



**error:用navicat连接如果报错**

![img](https://pic2.zhimg.com/80/v2-8bc25b3599b809c5787eebee4d70067d_hd.jpg)报错是因为加密算法变了

我们在docker里面改变加密算法

```text
mysql> grant all PRIVILEGES on *.* to root@'%' WITH GRANT OPTION;
Query OK, 0 rows affected (0.01 sec)
 
mysql> ALTER user 'root'@'%' IDENTIFIED BY '123456' PASSWORD EXPIRE NEVER;
Query OK, 0 rows affected (0.11 sec)
 
mysql> ALTER user 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
Query OK, 0 rows affected (0.11 sec)
 
mysql>  FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.01 sec)
```

算法换成mysql_native_password即可

### 3.2 user数据表建立

配置application.yml

```
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://192.168.253.133:3307/test?useUnicode=true&useSSL=false&characterEndcoding=utf8&useTimezone=true&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
```

创建表格

```
CREATE TABLE `user`  (
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'open_id',
  `skey` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'skey',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_visit_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `session_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'session_key',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网名',
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '微信用户信息' ;
```



## 第四章 springboot开发后端接口

### 4.1  entity

```
package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: create by calvin wong
 * @date:2019/12/10
 **/

@Data
@TableName("user")
public class User {
    /**
     * openid
     */
    @TableId(value = "open_id",type = IdType.INPUT)
    private String openId;
    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 用户网名
     */
    private String nickName;
    /**
     * session_key
     */
    private String session_key;
    /**
     * skey
     */
    private String skey;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 最后登录时间
     */
    @TableField("last_visit_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastVisitTime;

}

```

### 4.2 mapper

```
package top.weimumu.loginapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.weimumu.loginapi.entity.User;

/**
 * @author: create by calvin wong
 * @date:2019/12/4
 **/
public interface UserMapper extends BaseMapper<User> {

}

```

### 4.3 common封装工具类

GolbalResult

```
package top.weimumu.loginapi.common;

/**
 * @author: create by calvin wong
 * @date:2019/12/10
 **/
public class GlobalResult {
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    private String ok;	// 不使用

    public static GlobalResult build(Integer status, String msg, Object data) {
        return new GlobalResult(status, msg, data);
    }

    public static GlobalResult ok(Object data) {
        return new GlobalResult(data);
    }

    public static GlobalResult ok() {
        return new GlobalResult(null);
    }

    public static GlobalResult errorMsg(String msg) {
        return new GlobalResult(500, msg, null);
    }

    public static GlobalResult errorMap(Object data) {
        return new GlobalResult(501, "error", data);
    }

    public static GlobalResult errorTokenMsg(String msg) {
        return new GlobalResult(502, msg, null);
    }

    public static GlobalResult errorException(String msg) {
        return new GlobalResult(555, msg, null);
    }

    public GlobalResult() {

    }

    public GlobalResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public GlobalResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}

```

HttpClientUtils

```
package top.weimumu.loginapi.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: create by calvin wong
 * @date:2019/12/10
 **/
public class HttpClientUtil {
    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }

}

```

WechatUtil

```
package top.weimumu.loginapi.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by calvin wong
 * @date:2019/12/10
 **/
public class WechatUtil {
    public static JSONObject getSessionKeyOrOpenId(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        // https://mp.weixin.qq.com/wxopen/devprofile?action=get_profile&token=164113089&lang=zh_CN
        //小程序appId
        requestUrlParam.put("appid", "wx62e0151eaf62eff7");
        //小程序secret
        requestUrlParam.put("secret", "c45170b3aa8dbc5cd45ab12c319298f8");
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam));
        return jsonObject;
    }

    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
        }
        return null;
    }

}

```

### 4.3 controller

```
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

```

