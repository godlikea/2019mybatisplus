package com.kai.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TUserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6604140110905597878L;
	
	private Integer uid;
	
	private Integer rid;

}
