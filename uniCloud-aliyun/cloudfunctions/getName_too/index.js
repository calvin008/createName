'use strict';
const db = uniCloud.database()
exports.main = async (event, context) => {
	//event为客户端上传的参数
	let {shici} = event
	let res
	switch(shici) {
		case '唐诗':
		res = await db.collection('tangshi').aggregate().sample({
			size:6
		}).end()
		break;
		case '宋词':
			res = await db.collection('songci').aggregate().sample({
				size: 6
			}).end()
			break;
		
		case '楚辞':
			res = await db.collection('chuci').aggregate().sample({
				size: 6
			}).end()
			break;
		
		case '诗经':
			res = await db.collection('shijing').aggregate().sample({
				size: 6
			}).end()
			break;
		
		case '乐府':
			res = await db.collection('yuefu').aggregate().sample({
				size: 6
			}).end()
			break;
		
		case '古诗':
			res = await db.collection('gushi').aggregate().sample({
				size: 6
			}).end()
			break;
	}
	
	
	//返回数据给客户端
	return res
};
