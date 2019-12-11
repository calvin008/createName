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
		console.log(e.detail)
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
