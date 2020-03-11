<template>
	<view class="content">
		<form @submit="formSubmit">
			<!-- 宝宝姓氏 -->
			<view class="cu-form-group">
				<view class="title">宝宝姓氏</view>
				<input name="input" placeholder="请输入姓氏" maxlength="2" />
			</view>
			<!-- 宝宝性别 -->
			<view class="cu-form-group">
				<view class="title">宝宝性别</view>
				<radio-group name="radio1">
					<label>
						<radio class="black radio" :class="radio == 'A' ? 'checked' : ''" :checked="radio == 'A' ? true : false" value="A" />
						<text>男孩</text>
					</label>
					<label>
						<radio class="black radio" :class="radio == 'B' ? 'checked' : ''" :checked="radio == 'B' ? true : false" value="B" />
						<text>女孩</text>
					</label>
				</radio-group>
			</view>
			
			<!-- 名字字数 -->
			<view class="cu-form-group">
				<view class="title">名字字数</view>
				<radio-group name="radio2">
					<label>
						<radio class="black radio" :class="radio == 'C' ? 'checked' : ''" :checked="radio == 'C' ? true : false" value="C" />
						<text>单字名</text>
					</label>
					<label>
						<radio class="black radio" :class="radio == 'D' ? 'checked' : ''" :checked="radio == 'D' ? true : false" value="D" />
						<text>多字名</text>
					</label>
				</radio-group>
			</view>
			<!-- 日期选择 -->
			<view class="cu-form-group">
				<view class="title">出生日期</view>
				<picker name="datePicker" mode="date" :value="date" :start="startDate" :end="endDate"  @change="bindDateChange">
					<view class="picker">{{date}}</view>
				</picker>
			</view>
			<!-- 时辰选择 -->
			<view class="cu-form-group">
				<view class="title">出生时辰</view>
				<picker name="timePicker" mode="time" :value="time" start="09:01" end="21:01"  @change="bindTimeChange">
					<view class="picker">{{time}}</view>
				</picker>
			</view>
			<view class="margin"><button class="w-btn big radius bg-black margin-tb white-color" form-type="submit" >免费起名</button></view>
		</form>
		
	</view>
</template>

<script>

export default {
	
	data() {
		return {
			isBorn: true,
			date: '2020-2-2',
			posterShow: false,
			time: '12:01',
			radio: 'A'
		};
	},
	onLoad() {},
	methods: {
		bindDateChange: function(e) {
			this.date = e.target.value;
		},
		bindTimeChange: function(e) {
			this.time = e.target.value;
		},
		formSubmit: function (e) {
			console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.target.value))
			var formData = e.target.value;
			 uni.showToast({
			                    title: '提交成功',
			                    icon: 'success',
			                    duration: 1000
			                })
			setTimeout(() => {
			                   uni.navigateTo({
			                   	url:'../details/details'
			                   })
			                }, 600);
		}
	}
};
</script>

<style></style>
