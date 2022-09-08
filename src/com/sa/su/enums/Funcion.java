package com.sa.su.enums;

public enum Funcion {
	TRATAMIENTO(1, "TRATAMIENTO"),
	PARAMETRIA(2, "PARAMETRIA");
	
	int id;
	String descripcion;
	
	Funcion(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
