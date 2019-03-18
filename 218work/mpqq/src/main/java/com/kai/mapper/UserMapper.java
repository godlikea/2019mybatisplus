package com.kai.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kai.model.TUser;

public interface UserMapper extends BaseMapper<TUser> {
	
	@Select("select * from t_user ${ew.customSqlSegment}")
	List<TUser> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);
}
