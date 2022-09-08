package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUM_RUBRO")
public class Rubro extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 20180131L;

	@Id
	@SequenceGenerator(name="rubro_sequence",sequenceName="SUM_RUBRO_SEQ")
    @GeneratedValue(generator = "rubro_sequence")
	@Column(name = "ID")
	private Long id;
	@Size(min = 1, max = 50)
	@Column(name = "DESCRIPCION", nullable = false)
	private String descripcion;
	@Column(name = "NRO_SEC_TRATAMIENTO", columnDefinition="number(5,0) default 1", nullable= false)
	private Long nro_sec_tratamiento;
	@Column(name = "NRO_INTERFAZ", columnDefinition="number(4,0)" ,nullable = false)
	private Long nro_interfaz;
	public Rubro() {
	}
	public Rubro(Long id) {
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
	public Long getNro_sec_tratamiento() {
		return nro_sec_tratamiento;
	}
	public void setNro_sec_tratamiento(Long nro_sec_tratamiento) {
		this.nro_sec_tratamiento = nro_sec_tratamiento;
	}
	public Long getNro_interfaz() {
		return nro_interfaz;
	}
	public void setNro_interfaz(Long nro_interfaz) {
		this.nro_interfaz = nro_interfaz;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nro_interfaz == null) ? 0 : nro_interfaz.hashCode());
		result = prime * result + ((nro_sec_tratamiento == null) ? 0 : nro_sec_tratamiento.hashCode());
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
		Rubro other = (Rubro) obj;
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
		if (nro_interfaz == null) {
			if (other.nro_interfaz != null)
				return false;
		} else if (!nro_interfaz.equals(other.nro_interfaz))
			return false;
		if (nro_sec_tratamiento == null) {
			if (other.nro_sec_tratamiento != null)
				return false;
		} else if (!nro_sec_tratamiento.equals(other.nro_sec_tratamiento))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Rubro [id=" + id + ", descripcion=" + descripcion + ", nro_sec_tratamiento=" + nro_sec_tratamiento
				+ ", nro_interfaz=" + nro_interfaz + "]";
	}
}