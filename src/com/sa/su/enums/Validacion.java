package com.sa.su.enums;

public enum Validacion {
	STOCK(1, "STOCK"),
	CANTIDAD(2, "CANTIDAD"),
	PRESUPUESTO(3, "PARAMETRIA"),
	OK(4, "OK");
	
	long id;
	String descripcion;
	
	Validacion(long id, String descripcion) {
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
