package com.sa.su.beans;

import java.io.Serializable;

public class CRUDStatus extends StatusBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	public CRUDStatus(Long crudId,String status) {
		super(status);
		
		this.id=crudId;	
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
