# uni-app微信小程序登录

## 第一章 前端页面

### 1.1 简介

前端使用uni-app

后端springboot2.X+mybatis plus

持久化数据库mysql8.0.16

![image](https://res.wx.qq.com/wxdoc/dist/assets/img/api-login.2fcc9f35.jpg)

### 1.2 微信小程序登录步骤

第一步：小程序通过uni.login()获取code。
第二步：小程序通过uni.request()发送code到开发者服务器。
第三步：开发者服务器接收小程序发送的code，并携带appid、appsecret（这两个需要到微信小程序后台查看）、code发送到微信服务器。
第四步：微信服务器接收开发者服务器发送的appid、appsecret、code进行校验。校验通过后向开发者服务器发送session_key、openid。
第五步：开发者服务器自己生成一个key（自定义登录状态）与openid、session_key进行关联，并存到数据库中（mysql、redis等）。
第六步：开发者服务器返回生成key（自定义登录状态）到小程序。
第七步：小程序存储key（自定义登录状态）到本地。

### 1.3首页index

```
<template>
	<view class="content">
		<image class="logo" :src="userInfo.avatarUrl || '/static/missing-face.png'"></image>
		<view class="text-area">
			<text class="title" @click="toLogin">{{ hasLogin ? userInfo.username || '未设置昵称' : '立即登录' }}</text>
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
		<button class="confirm-btn" open-type="getUserInfo" @getuserinfo="wxgetUserInfo" :disabled="logining">微信授权登录</button>
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
		wxgetUserInfo() {
			let that = this;
			that.logining = true;
			uni.showLoading({
				title: '登录中'
			});
			uni.getUserInfo({
				provider: 'weixin',
				success: res => {
					let avatarUrl = res.userInfo.avatarUrl;
					let nickName = res.userInfo.nickName;
					console.log(avatarUrl);
					console.log(nickName);
					try {
						uni.setStorageSync();
						that.updateUserInfo();
					} catch (e) {
						//TODO handle the exception
					}
				}
			});

			uni.login({
				success(loginRes) {
					let code = loginRes.code;
					console.log(code);
					// 将用户code传给后台置换openid和session_key
					uni.request({
						url: '127.0.0.1:8080/login',
						method: 'post',
						data: {
							code : code
						},
						header: {
							'content-type': 'application/json'
						},
						success(resultRes) {
							
						}
						
					});
				}
			});
			
			uni.hideLoading();
			uni.navigateBack();
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

### 2.2 登录功能实现

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
  url: jdbc:mysql://192.168.253.133:3307/test?useUnicode=true&useSSL=false&characterEndcoding=utf8&serverTimezon=Asia/Shanghai
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

/**
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
