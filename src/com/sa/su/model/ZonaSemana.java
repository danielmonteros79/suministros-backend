package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "SUM_ZONA_SEMANA")
public class ZonaSemana extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 20181210L;

	@Id
	@ManyToOne
	@JoinColumn(name = "ID_ZONA")
	@ForeignKey(name = "FK_ZONA_SEMANA_ZONA")
	private Zona id_zona;

	@Id
	@Column(name = "SEMANA", nullable = false, columnDefinition = "number")
	private Integer semana;

	public Zona getId() {
		return id_zona;
	}

	public void setId(Zona id_zona) {
		this.id_zona = id_zona;
	}

	public Integer getSemana() {
		return semana;
	}

	public void setSemana(Integer semana) {
		this.semana = semana;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id_zona == null) ? 0 : id_zona.hashCode());
		result = prime * result + ((semana == null) ? 0 : semana.hashCode());
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
		ZonaSemana other = (ZonaSemana) obj;
		if (id_zona == null) {
			if (other.id_zona != null)
				return false;
		} else if (!id_zona.equals(other.id_zona))
			return false;
		if (semana == null) {
			if (other.semana != null)
				return false;
		} else if (!semana.equals(other.semana))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ZonaSemana [id_zona=" + id_zona + ", semana=" + semana + "]";
	}

}
