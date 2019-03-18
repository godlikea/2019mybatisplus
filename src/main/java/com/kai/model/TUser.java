package com.kai.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("t_user")
@Data
public class TUser implements Serializable{

	private static final long serialVersionUID = 7603384688420692645L;
	@TableId(type=IdType.AUTO)
	private Integer id;
	
	private String name;

}
