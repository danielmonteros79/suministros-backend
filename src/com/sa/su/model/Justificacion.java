package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUM_JUSTIFICACIONES")
public class Justificacion extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 20180207L;

	@Id
	@SequenceGenerator(name = "justificaciones_sequence", sequenceName = "SUM_JUSTIFICACIONES_SEQ")
	@GeneratedValue(generator = "justificaciones_sequence")
	@Column(name = "ID")
	private Long id;
	
	@Size(min = 1, max = 50)
	@Column(name = "DESCRIPCION", nullable = false)
	private String descripcion;

	public Justificacion() {
	}

	public Justificacion(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Justificacion other = (Justificacion) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Justificacion [id=" + id + ", descripcion=" + descripcion + "]";
	}
}