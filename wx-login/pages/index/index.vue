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
