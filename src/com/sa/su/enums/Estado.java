package com.sa.su.enums;

public enum Estado {
	PENDIENTE(1, "PENDIENTE"),
	VALIDACION(2, "VALIDACION"),
	APROBADO(3, "APROBADO"),
	RECHAZADO(4, "RECHAZADO"),
	TRATADO(5, "TRATADO"),
	RECIBIDO(6, "RECIBIDO");
	
	long id;
	String descripcion;
	
	Estado(long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
