package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SUM_PERMISO")
public class Permiso extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COD_AREA", columnDefinition = "number(8,0)")
	private Integer codArea;

	@Id
	@Column(name = "COD_PUESTO", columnDefinition = "number(8,0)")
	private Integer codPuesto;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FUNCION", nullable = false)
	private Funcion funcion;

	public Integer getCodArea() {
		return codArea;
	}

	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}

	public Integer getCodPuesto() {
		return codPuesto;
	}

	public void setCodPuesto(Integer codPuesto) {
		this.codPuesto = codPuesto;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codArea == null) ? 0 : codArea.hashCode());
		result = prime * result + ((codPuesto == null) ? 0 : codPuesto.hashCode());
		result = prime * result + ((funcion == null) ? 0 : funcion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permiso other = (Permiso) obj;
		if (codArea == null) {
			if (other.codArea != null)
				return false;
		} else if (!codArea.equals(other.codArea))
			return false;
		if (codPuesto == null) {
			if (other.codPuesto != null)
				return false;
		} else if (!codPuesto.equals(other.codPuesto))
			return false;
		if (funcion == null) {
			if (other.funcion != null)
				return false;
		} else if (!funcion.equals(other.funcion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permiso [codArea=" + codArea + ", codPuesto=" + codPuesto + ", funcion=" + funcion + "]";
	}
}