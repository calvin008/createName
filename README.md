# uni-app微信小程序登录

## 第一章 前端页面

### 1.1 简介

前端使用uni-app

后端springboot2.X+mybatis plus

持久化数据库mysql8.0.16

![image](https://github.com/calvin008/weixin-login/blob/master/image/login-api.jpg)

![image](https://github.com/calvin008/weixin-login/blob/master/image/show.gif)

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