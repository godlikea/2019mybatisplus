package com.kai.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

@Data
public class TRole extends Model<TRole> {

	
	private Integer id;
	
	private String role;

}
