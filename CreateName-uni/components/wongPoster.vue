<template>
	<view>
		<!-- 海报 -->
		<canvas canvas-id="wongPoster" :style="{width:cansWidth,height:cansHeight}" class="isCan"></canvas>
		<view class="bg" @click="closeCans"></view>
		<view class="fixedBox">
			<view class="boxLeft"><button class="flexBtn btnLeft" hover-class="btnHover" @click="saveCans">保存</button></view>
			<view class="boxRight"><button class="flexBtn btnRight" hover-class="btnHover" @click="closeCans">关闭</button></view>
		</view>
	</view>
</template>

<script>
	export default {
		props:{

		},
		data() {
			return {
				// 海报
				cansWidth:270, //海报宽度
				cansHeight:480 	//海报高度
				// 海报
			};
		},
		created() {
			this.ctx = uni.createCanvasContext('wongPoster',this);
			//绘制海报底色为白色
			this.drawBaseBg('white');
			/*
				绘制图片
				调用方式:this.drawBg({
					url:'',路径
					sLeft:0~1 | 'center' 百分比离左边距离 1则为100vw,
					sTop:0~1 百分比离顶部距离,
					sWidth:0~1 百分比宽度，
					sHeight:0~1 百分比高度
				})
			*/	
			this.drawBg({url:'/static/can_bg.png',sLeft:0,sTop:0,sWidth:1,sHeight:0.75})
			this.drawBg({url:'/static/scan.jpg',sLeft:.05,sTop:0.80,sWidth:.30,sHeight:0.15})
			/*
				绘制头像
				绘制头像需要添加域名白名单	downloadFile合法域名	https://wx.qlogo.cn
				调用方式:this.circleImg(img, x, y, r) 
				img:图片路径
				x:0~1 百分比离左边距离,
				y:0~1 百分比离顶部距离
			*/
			this.circleImg('http://wx.qlogo.cn/mmopen/g1fhzkUAKGxa6OShKmbCB2nz2BvaGJzdjwGDduibvTLwmHChCiaFHxHI71tFIJyQfMHNxDibSJZ30TUgRKTEhEGonmRj3icNeEht/64',.35,.23, 12)
			/*
				绘制单行文本
				调用方式:this.drawText({
					text:'文本', String || Array
					sLeft:0~1 || 'center' 百分比离左边距离,
					sTop:0~1 百分比离顶部距离,
					fontSize:Number 文字大小,
					color:'' 颜色,
					bold:Boolean 粗体,
					lineHeight:Number //如果是数组则设置行高
				})
			*/	 
			this.drawText({text:'恭喜您获得免费好名字',sLeft:'center',sTop:0.1,fontSize:12,color:'#5A390F'})
			this.drawText({text:'王金富',sLeft:.48,sTop:0.265,fontSize:12,color:'#5A390F'})
			this.drawText({text:'王金富',sLeft:.67,sTop:0.53,fontSize:10,color:'#5A390F'})
			this.drawText({text:this.today,sLeft:.64,sTop:0.58,fontSize:10,color:'#5A390F'})
			this.drawText({text:'求财得财',sLeft:.1,sTop:0.65,fontSize:10,color:'#5A390F',bold:true})
			this.drawText({text:['XXXXXXXXXXXXXXXX','XXXXXXXXX','XXXXXXXX'],sLeft:.37,sTop:0.85,fontSize:10,color:'#5A390F',lineHeight:12})
			// 绘制多行文本
			this.drawPara({
					para:'姓名评分为：88，配合您的生辰八字属于求财得财，大富大贵；您可以保存图片分享给亲友。',//文本
					fontSize:10,//字体大小
					sLeft:.06,//百分比离左边距离
					sTop:.32,//百分比李作边距离
					titleHeight:16,//一行高度
					sMaxWidth:.84,//一行最大宽度
					redWord:['2020年6月6日','姓名评分为：88'],//关键字
					color:'#5A390F',//正常颜色
					redColor:'#DD524D',//高亮颜色
					bold:false //是否加粗
			})
		},
		computed:{
			today(){
				let year = new Date().getFullYear();
				let month =new Date().getMonth() + 1 
				let date =new Date().getDate()
				let time = year + "年" + month + "月" + date +"日"
				return time
			}
		},
		methods:{
			drawBaseBg(bgColor){
				this.ctx.rect(0, 0, this.cansWidth, this.cansHeight)
				this.ctx.setFillStyle(bgColor)
				this.ctx.fill()
				this.ctx.draw(true)
			},
			circleImg(img, x, y, r) {
				uni.getImageInfo({
					src:img
				}).then((image)=>{
					console.log(image[1].path)
					x= Math.ceil(this.cansWidth * x)
					y = Math.ceil(this.cansHeight * y)
					this.ctx.save();
					var d = 2 * r;
					var cx = x + r;
					var cy = y + r;
					this.ctx.arc(cx, cy, r, 0, 2 * Math.PI);
					this.ctx.clip();
					this.ctx.drawImage(image[1].path, x, y, d, d);
					this.ctx.restore();
					this.ctx.draw(true);
				})
			},
			closeCans() {
				this.$parent.posterShow = false
			},
		saveCans(){
			console.log('保存')
			uni.showLoading({
				title:'保存ing...',
				mask:true
			})
			uni.canvasToTempFilePath({
			  x: 0,
			  y: 0,
			  width: this.cansWidth*1.5,
			  height: this.cansHeight * 1.5,
			  destWidth: this.cansWidth * 3,
			  destHeight: this.cansHeight * 3,
			  canvasId:'wongPoster',
			  success: function(res) {
				uni.hideLoading()
				uni.saveImageToPhotosAlbum({
					filePath: res.tempFilePath,
					success: function (res) {
						uni.showToast({
							title:'保存相册成功'
						})
						console.log('save success')
					},
				  fail(res) {
					console.log(res)
					if(res.errMsg == "saveImageToPhotosAlbum:fail auth deny") {
						uni.showModal({
							title:'您需要授权相册权限',
							success(res) {
								if(res.confirm){
									uni.openSetting({
										success(res) {
										
										},
										fail(res) {
											console.log(res)
										}
									})
								}
							}
						})
					}
				  }
				});
			  },
			  fail(res) {
				  uni.hideLoading()
			  }
			},this)
		},
			drawPara(item){
				var redIndexObj = {}
				if(item.redWord.length > 0){
					for(var i = 0; i < item.redWord.length ;i++){
						let startIndex = 0,index;
						 while ((index = item.para.indexOf(item.redWord[i], startIndex)) > -1) {
						        redIndexObj[index] = true;
						        for(var j = 0;j<item.redWord[i].length; j++){
									redIndexObj[index+j] = true
								}
								startIndex = index + 1;
						 }
					}
				}
				this.ctx.font =`normal normal ${item.fontSize}px sans-serif`;
				this.ctx.setFillStyle(item.color)
				var isLeft 
				if(item.sLeft == 'center'){
					isLeft = this.cansWidth * (.5 - item.sMaxWidth /2) - item.fontSize
				} else {
					isLeft =  item.sLeft * this.cansWidth
				}
				var maxWidth = Math.floor(this.cansWidth * item.sMaxWidth)
				var tempList = item.para.split('')
				var nowText = '',
				isCol = 0,
				textWidth = 0
				for(var i = 0 ; i <tempList.length;i++){
					if(i>0){
						nowText += tempList[i -1]
					}  else {
						nowText += tempList[0]
					}
					textWidth = this.ctx.measureText(nowText).width
					if(textWidth > maxWidth){
						isCol++
						nowText = '占'
						textWidth = this.ctx.measureText(nowText).width
					}
					if(redIndexObj[i]){
						this.ctx.save()
						if(item.bold) this.ctx.font =`normal bold ${item.fontSize}px sans-serif`;
						this.ctx.setFillStyle(item.redColor)
					}
					this.ctx.fillText(tempList[i],isLeft+textWidth, item.sTop * this.cansHeight + item.titleHeight * isCol)
					this.ctx.restore()
				}
				this.ctx.draw(true)
			},
			drawBg(item){
				if(item.sLeft == 'center'){
				this.ctx.drawImage(item.url, this.cansWidth * (.5 - item.sWidth /2) , this.cansHeight * item.sTop, this.cansWidth * item.sWidth, this.cansHeight * item.sHeight);
				} else {
				this.ctx.drawImage(item.url, this.cansWidth * item.sLeft, this.cansHeight * item.sTop, this.cansWidth * item.sWidth, this.cansHeight * item.sHeight);
				}
				this.ctx.draw(true);
			},
			drawText(item){
				var isLeft
				if(item.sLeft == 'center'){
					isLeft = this.cansWidth * .5 - (item.fontSize*item.text.length) /2
				} else {
					isLeft =  item.sLeft * this.cansWidth
				}
				this.ctx.save()
				if(item.bold) this.ctx.font =`normal bold ${item.fontSize}px sans-serif`;
				this.ctx.setFillStyle(item.color)
				this.ctx.setFontSize(item.fontSize)
				if(item.text instanceof Array){
					if(!item.lineHeight) item.lineHeight = item.fontSize + 2
					console.log('我是数组',item.text)
					for(var i = 0 ; i < item.text.length ; i++){
						this.ctx.fillText(item.text[i],isLeft, item.sTop * this.cansHeight + item.lineHeight * i)
					}
				} else{
					console.log('我是字符串',item.text)
					this.ctx.fillText(item.text,isLeft, item.sTop * this.cansHeight)
				}
				this.ctx.draw(true)
				this.ctx.restore()
			}
		}
	}
</script>

<style lang="scss" scoped>
	.bg{
		width: 100vw;
		height: 100vh;
		position: fixed;
		left: 0;
		top: 0;
		z-index: 998;
		background-color: rgba(0, 0, 0, 0.8);
	}
	.fixedBox{
		width: 100%;
		height: 100upx;
		position: fixed;
		bottom: 30upx;
		left: 0;
		display: flex;
		z-index: 1000;
		.boxLeft,.boxRight{
			width: 50%;
			height: 100%;
			display: flex;
			align-items: center;
			position: relative;
			z-index: 1000;
			justify-content: center;
			.flexBtn{
				position: relative;
				z-index: 1000;
				width: 200upx;
				height: 60upx;
				text-align: center;
				line-height: 55upx;
				font-size: 24upx;
				border-bottom: 6upx #f58365 solid;
				border-radius: 32upx;
				color: white;
				background: linear-gradient(to left, #f58365, #ff188a);
			}
		}
	}
	.btnHover{
		height: 55upx !important;
		border-bottom: 0 #F6BE3C solid !important;
		transform: translateY(3px) translateZ(0px) !important;
	}
	.isCan{
		border: 6px solid white;
		border-radius: 10px;
		position: fixed;
		left: 0;
		z-index: 999;
		width: 270px;
		height: 480px;
		right: 0;
		bottom: 130upx;
		margin:0 auto;
		background-size: 100%;
	}
</style>
