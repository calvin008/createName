<template>
	<view class="content">
		<!-- 登录头像 -->
		<image class="logo" :src="userInfo.avatarUrl || '/static/missing-face.png'"></image>
		<view class="text-area">
			<text class="title" @click="toLogin">{{ hasLogin ? userInfo.nickName || '未设置昵称' : '立即登录' }}</text>
		</view>
		<!-- list列表 -->
		<view class="section">
			<list-cell icon="icon-chuangzuo" iconColor="#8a8a8a" title="起名记录"></list-cell>
			<list-cell icon="icon-dingdan" iconColor="#8a8a8a" title="测名记录"></list-cell>
			<list-cell icon="icon-dianhua" iconColor="#8a8a8a" title="联系客服"></list-cell>
		</view>
	</view>
</template>

<script>
import { mapState } from 'vuex';
import listCell from '../../components/list-cell.vue';
export default {
	components:{
		listCell
	},
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
.section {
	padding: 30upx 0 0;
	margin-top: 40upx;
	background: #fff;
	border-radius:10upx;
}
</style>
