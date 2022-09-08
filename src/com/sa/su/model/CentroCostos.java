package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUM_CENTRO_COSTOS")
public class CentroCostos extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "centroCostos_sequence", sequenceName = "SUM_CENTRO_COSTOS_SEQ")
	@GeneratedValue(generator = "centroCostos_sequence")
	@Column(name = "ID")
	private Long id;
	
	@Size(min = 1, max = 50)
	@Column(name = "DESCRIPCION", nullable = false)
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CONSUMO", nullable = false)
	private Consumo consumo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ZONA", nullable = false)
	private Zona zona;
	
	@Size(max = 50)
	@Column(name = "DIRECCION1")
	private String direccion1;
	
	@Size(max = 50)
	@Column(name = "DIRECCION2")
	private String direccion2;
	
	@Size(max = 50)
	@Column(name = "DIRECCION3")
	private String direccion3;
	
	@Column(name = "TELEFONO", columnDefinition = "number(12,0)")
	private Integer telefono;
	
	@Size(max = 30)
	@Column(name = "CONTACTO")
	private String contacto;
	
	@Size( max = 4)
	@Column(name = "CODPOSTAL")
	private String codpostal;
	

	public String getCodpostal() {
		return codpostal;
	}

	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}

	public CentroCostos() {
	}

	public CentroCostos(Long id) {
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

	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public String getDireccion1() {
		return direccion1;
	}

	public void setDireccion1(String direccion1) {
		this.direccion1 = direccion1;
	}

	public String getDireccion2() {
		return direccion2;
	}

	public void setDireccion2(String direccion2) {
		this.direccion2 = direccion2;
	}

	public String getDireccion3() {
		return direccion3;
	}

	public void setDireccion3(String direccion3) {
		this.direccion3 = direccion3;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((consumo == null) ? 0 : consumo.hashCode());
		result = prime * result + ((contacto == null) ? 0 : contacto.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((direccion1 == null) ? 0 : direccion1.hashCode());
		result = prime * result + ((direccion2 == null) ? 0 : direccion2.hashCode());
		result = prime * result + ((direccion3 == null) ? 0 : direccion3.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((zona == null) ? 0 : zona.hashCode());
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
		CentroCostos other = (CentroCostos) obj;
		if (consumo == null) {
			if (other.consumo != null)
				return false;
		} else if (!consumo.equals(other.consumo))
			return false;
		if (contacto == null) {
			if (other.contacto != null)
				return false;
		} else if (!contacto.equals(other.contacto))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (direccion1 == null) {
			if (other.direccion1 != null)
				return false;
		} else if (!direccion1.equals(other.direccion1))
			return false;
		if (direccion2 == null) {
			if (other.direccion2 != null)
				return false;
		} else if (!direccion2.equals(other.direccion2))
			return false;
		if (direccion3 == null) {
			if (other.direccion3 != null)
				return false;
		} else if (!direccion3.equals(other.direccion3))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (zona == null) {
			if (other.zona != null)
				return false;
		} else if (!zona.equals(other.zona))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CentroCostos [id=" + id + ", descripcion=" + descripcion + ", consumo=" + consumo + ", zona=" + zona + ", direccion1=" + direccion1
				+ ", direccion2=" + direccion2 + ", direccion3=" + direccion3 + ", telefono=" + telefono + ", contacto=" + contacto + "]";
	}

}