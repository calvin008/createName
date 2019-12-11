import Vue from 'vue'
import store from './store'
import App from './App'
import * as config from './config'

const defConfig = config.def

const msg = (title, duration = 1500, mask = false, icon = 'none') => {
	//统一提示方便全局修改
	if (Boolean(title) === false) {
		return;
	}
	uni.showToast({
		title,
		duration,
		mask,
		icon
	});
}

const request = (_gp, _mt, data = {}, failCallback) => {
	//异步请求数据
	return new Promise(resolve => {
		if (!userInfo || !userInfo.accessToken) {
			userInfo = uni.getStorageSync('userInfo')
		}
		let accessToken = userInfo ? userInfo.accessToken : ''
		let baseUrl = config.def().baseUrl
		uni.request({
			url: baseUrl + '/m.api',
			data: {
				...data,
				_gp,
				_mt
			},
			method: 'POST',
			header: {
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
				'ACCESSTOKEN': accessToken
			},
			success: (res) => {
				if (res.statusCode === 200) {
					if (res.data.errno === 200) {
						resolve(res.data);
					} else if (res.data.errno === 10001) {
						if (failCallback) {
							failCallback(res.data)
						}
						if (!loginLock) {
							loginLock = true
							uni.showModal({
								title: '登录提示',
								content: '您尚未登录，是否立即登录？',
								showCancel: true,
								confirmText: '登录',
								success: (e) => {
									if (e.confirm) {
										uni.navigateTo({
											url: '/pages/public/login'
										})
									}
								},
								fail: () => {},
								complete: () => {
									loginLock = false
								}
							})
						}

					} else {
						if (failCallback) {
							failCallback(res.data)
						} else {
							uni.showToast({
								title: res.data.errmsg,
								icon: 'none'
							})
						}
					}
				}
			}
		})
	})
}

let userInfo = undefined

const logout = () => {
	userInfo = undefined
	uni.removeStorage({
		key: 'userInfo'
	})
}

const setUserInfo = (i) => {
	userInfo = i
}

Vue.config.productionTip = false
Vue.prototype.$store = store;
Vue.prototype.$api = {
	request,
	setUserInfo,
	logout,
	msg,
	defConfig
};


App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
