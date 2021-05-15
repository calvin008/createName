<template>
	<view class="content">
		<view>
			<radio-group class="radio-group" @change="radioChange">
				<label class="radio" v-for="(item, index) in array" :key="index">
					<view><radio :value="item" color="#000000" :checked="index === current" /></view>
					<view>{{ item }}</view>
				</label>
			</radio-group>
		</view>
		<view class="form-item">
			<image class="img" src="../../static/icon_search.png"></image>
			<input type="text" v-model="userName" placeholder="请输入姓氏" />
			<button class="button" type="primary" size="mini" @click="subName">起名</button>
		</view>
		<view class="name-list">
			<view class="name-container" v-for="item in nameList" :key="item._id">
				<view class="name-info">
					<view>{{ userName }}</view>
					<view v-for="(name,index) in item.name" :key="index">{{name}}</view>
				</view>
				<view class="sentence">
					<view>[</view>
					<view v-for="(sentence,index) in item.sentence" :key="index"
					:class="tools.fn(item.name, sentence)?'active':'black'">
						{{sentence}}
					</view>
					<view>]</view>
				</view>
				<view class="name-other">
					<view>{{ item.book }} ● {{ item.title }}</view>
					<view>[{{ item.dynasty }}] {{ item.author }}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script module="tools" lang="wxs">
	function fn(arr, value) {
		if(arr.indexOf(value) < 0) {
			return false;
		} else {
			return true;
		}
	}
	module.exports = {
		fn:fn
	};
</script>
<script>
export default {
	data() {
		return {
			array: ['唐诗', '宋词', '诗经', '楚辞', '乐府', '古诗'],
			current: 0,
			userName: '',
			nameList: ''
		};
	},
	onLoad() {},
	methods: {
		radioChange(evt) {
			for (let i = 0; i < this.array.length; i++) {
				if (this.array[i] === evt.target.value) {
					this.current = i;
					break;
				}
			}
		},
		// 选取诗文
		randBetween(min, max) {
			// [min,max)  max is not included
			return min + Math.floor(Math.random() * (max - min));
		},
		// 清理标点符号
		cleanPunctuation(str) {
			const puncReg = /[<>《》！*\(\^\)\$%~!@#…&%￥—\+=、。，？；‘’“”：·`]/g;
			return str.replace(puncReg, '');
		},

		// 取两个字
		randCharFromStr(str, num, ordered) {
			if (typeof ordered === 'undefined') {
				ordered = true;
			}
			let randNumArr = this.genRandNumArr(str.length, num);
			if (ordered) {
				randNumArr = randNumArr.sort((a, b) => a - b);
			}
			let res = '';
			for (let i = 0; i < randNumArr.length; i++) {
				res += str.charAt(randNumArr[i]);
			}
			return res;
		},
		genRandNumArr(max, num) {
			if (num > max) {
				num = max;
				console.log(`max=${max} num = ${num}`);
				// throw new Error('too large num');
			}
			const orderedNum = [];
			for (var i = 0; i < max; i++) {
				orderedNum.push(i);
			}
			const res = [];
			for (var i = 0; i < num; i++) {
				const randIndex = this.randBetween(0, orderedNum.length);
				const randNum = orderedNum[randIndex];
				res.push(randNum);
				orderedNum.splice(randIndex, 1);
				// console.log('i=' + i + 'rand=' + rand, orderedNum);
			}
			return res;
		},
		async subName() {
			let arr = Object.keys(this.userName);
			let reg = /^([\u4E00-\u9FA5])*$/;
			const shici = this.array[this.current];
			if (arr.length == 0) {
				uni.showModal({
					title: '姓氏不能为空'
				});
			} else if (arr.length > 2) {
				uni.showModal({
					title: '姓氏不能超过两位'
				});
			} else if (!reg.test(this.userName)) {
				uni.showModal({
					title: '姓氏必须是汉字'
				});
			} else {
				await uniCloud
					.callFunction({
						name: 'getName_too',
						data: {
							shici
						}
					})
					.then(res => {
						const List = res.result.data;
						const Result = [];

						List.forEach((item, index) => {
							var object = {};
							object = item.content;
							object = object.replace(/\s|<br>|<p>|<\/p>|　|”|“/g, '');
							object = object.replace(/\(.+\)/g, '');
							object = object.replace(/！|。|？|；/g, object => `${object}|`);
							object = object.replace(/\|$/g, '');
							let arr = object.split('|');
							arr = arr.filter(item => item.length >= 2);
							const sentence = arr[this.randBetween(0, arr.length)];
							const cleanSentence = this.cleanPunctuation(sentence);
							const name = this.randCharFromStr(cleanSentence, 2);

							var str = {};
							str.sentence = sentence.split("");
							str.name = name.split("");
							str.title = item.title;
							str.author = item.author ? item.author : '佚名';
							str.book = item.book;
							str.dynasty = item.dynasty;
							Result.push(str);
						});
						this.nameList = Result;
					});
			}
		}
	}
};
</script>

<style lang="scss">
.content {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}
.radio-group {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	margin-bottom: 10px;
	.radio {
		display: flex;
		flex-wrap: nowrap;
		width: 50%;
		justify-content: center;
		margin-bottom: 5px;
	}
}

.form-item {
	display: flex;
	flex-wrap: nowrap;
	height: 10%;
	margin: 10px, 0;
	background-color: #c8c7cc;
	border-radius: 10px;
	.img {
		width: 20px;
		height: 20px;
		padding: 2px;
	}
	.button {
		background-color: #000000;
		color: #ffffff;
	}
}

.name-list {
	display: flex;
	flex-direction: column;
		
	.name-container {
		margin: 10px 20px;
		padding: 5px 5px;
		background-color: #f1f1f1;
		border: #333333 solid 1px;
		.name-info {
			font-size: 26px;
			font-weight:bold;
			display: flex;
			flex-wrap: nowrap;
		}
		.sentence {
			padding-top:5px;
			padding-bottom: 5px;
			padding-left: 30px;
			display: flex;
			flex-wrap: nowrap;
			.active {
				color: #007AFF;
			}
			.black {
				color: #333333;
			}
		}
		
		.name-other {
			
			pading:2px 2px
			
		}
	}
	
}
</style>
