package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUM_USUARIO_CENTRO_COSTO")
public class UsuarioCentroCosto extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 20180117L;

	@Id
	@Size(min = 7, max = 7)
	@Column(name = "LEGAJO", nullable = false)
	private String legajo;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CENTRO_COSTOS", nullable = false)
	private CentroCostos centroCostos;

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public CentroCostos getCentroCostos() {
		return centroCostos;
	}

	public void setCentroCostos(CentroCostos centroCostos) {
		this.centroCostos = centroCostos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((centroCostos == null) ? 0 : centroCostos.hashCode());
		result = prime * result + ((legajo == null) ? 0 : legajo.hashCode());
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
		UsuarioCentroCosto other = (UsuarioCentroCosto) obj;
		if (centroCostos == null) {
			if (other.centroCostos != null)
				return false;
		} else if (!centroCostos.equals(other.centroCostos))
			return false;
		if (legajo == null) {
			if (other.legajo != null)
				return false;
		} else if (!legajo.equals(other.legajo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioCentroCosto [legajo=" + legajo + ", centroCostos=" + centroCostos + "]";
	}
}