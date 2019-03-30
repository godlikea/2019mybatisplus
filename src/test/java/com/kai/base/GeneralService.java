package com.kai.base;

import com.alibaba.fastjson.JSONObject;

/**
 * 通用service
 * @author ggk
 * @data 2019年3月29日下午3:12:24
 */
public class GeneralService {
	
	private BaseCrud  baseCrud;
	
	/**
	 * 创建表    
	 * 创建时  默认列为：
	 * id 主键   String   uuid
	 * createTime data  创建时间
	 * delete  int  0：删除 1，为删除  or 禁用状态 0：禁用 ,1，启用
	 * 
	 * 数据库类型标注,1:String 2:int 3:data
	 * 如有需要其他类型,随时补充间
	 * @author ggk
	 * @data 2019年3月29日下午3:14:17
	 * @param jsonObject  jsonObject 格式：{表名：ext,column:[{columnName:,type:},{},{}]}
	 * @return
	 */
	public int  createTable(JSONObject jsonObject) {
		return 0;
	}
	
	/**
	 * 插入数据库时操作
	 * @author ggk
	 * @data 2019年3月29日下午4:04:02
	 * @param jsonObject   格式 :{表名：ext,column:[{columnName:,value:},{},{}]}
	 * @return
	 */
	public int insert(JSONObject jsonObject) {
		return 0;
	}
	
	/**
	 * 删除操作
	 * @author ggk
	 * @data 2019年3月29日下午4:11:39
	 * @param jsonObject 格式 :{表名：ext,id:]}
	 * @return
	 */
	public int delete(JSONObject jsonObject) {
		return 0;
	}
	/**
	 * 修改操作
	 * @author ggk
	 * @data 2019年3月29日下午4:12:43
	 * @param jsonObject   格式 :{表名：ext,id:"",column:[{columnName:,value:},{},{}]}
	 * @return
	 */
	public int update(JSONObject jsonObject) {
		return 0;
	}
}
