package com.sa.su.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "SUM_USUARIO")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LEGAJO")
	private String legajo;

	@Column(name = "COD_SECTOR", columnDefinition = "number(8,0)")
	private Integer codSector;

	@Column(name = "DESC_SECTOR")
	private String descSector;

	@Column(name = "COD_AREA", columnDefinition = "number(8,0)")
	private Integer codArea;

	@Column(name = "DESC_AREA")
	private String descArea;

	@Column(name = "COD_PUESTO", columnDefinition = "number(8,0)")
	private Integer codPuesto;

	@Column(name = "DESC_PUESTO")
	private String descPuesto;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "APELLIDO")
	@Size(min = 1, max = 50)
	private String apellido;

	@Column(name = "NOMBRE")
	@Size(min = 1, max = 50)
	private String nombre;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CENTRO_COSTOS_ID")
	@ForeignKey(name = "FK_USUARIO_CC")
	private CentroCostos centroCostos;

	@Transient
	private List<Integer> funciones;

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public Integer getCodSector() {
		return codSector;
	}

	public void setCodSector(Integer codSector) {
		this.codSector = codSector;
	}

	public String getDescSector() {
		return descSector;
	}

	public void setDescSector(String descSector) {
		this.descSector = descSector;
	}

	public Integer getCodArea() {
		return codArea;
	}

	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}

	public String getDescArea() {
		return descArea;
	}

	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}

	public Integer getCodPuesto() {
		return codPuesto;
	}

	public void setCodPuesto(Integer codPuesto) {
		this.codPuesto = codPuesto;
	}

	public String getDescPuesto() {
		return descPuesto;
	}

	public void setDescPuesto(String descPuesto) {
		this.descPuesto = descPuesto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CentroCostos getCentroCostos() {
		return centroCostos;
	}

	public void setCentroCostos(CentroCostos centroCostos) {
		this.centroCostos = centroCostos;
	}

	public List<Integer> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Integer> funciones) {
		this.funciones = funciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((legajo == null) ? 0 : legajo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (legajo == null) {
			if (other.legajo != null)
				return false;
		} else if (!legajo.equals(other.legajo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [legajo=" + legajo + ", codSector=" + codSector + ", descSector=" + descSector + ", codArea=" + codArea + ", descArea="
				+ descArea + ", codPuesto=" + codPuesto + ", descPuesto=" + descPuesto + ", email=" + email + ", apellido=" + apellido + ", nombre="
				+ nombre + ", centroCostos=" + centroCostos + ", funciones=" + funciones + "]";
	}

}