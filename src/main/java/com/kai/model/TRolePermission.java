package com.kai.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TRolePermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415116633896466303L;
	
	private Integer rid;
	
	private Integer pid;

}
