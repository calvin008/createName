<script>
import { mapMutations } from 'vuex';
export default {
	methods: {
		...mapMutations(['login'])
	},
	onLaunch: function() {
		let userInfo = uni.getStorageSync('userInfo') || '';
		if (userInfo.id) {
			//更新登陆状态
			uni.getStorage({
				key: 'userInfo',
				success: res => {
					this.login(res.data);
				}
			});
		}
	},
	onShow: function() {
		console.log('App Show');
	},
	onHide: function() {
		console.log('App Hide');
	}
};
</script>

<style>
/*每个页面公共css */
/*每个页面公共css */
@import 'colorui/main.css';
@import 'colorui/icon.css';
</style>
