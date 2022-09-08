package com.sa.su.model;

import java.math.BigDecimal;

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
@Table(name = "SUM_MATERIAL")
public class Material extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SUM_MATERIAL_SEQ",sequenceName="SUM_MATERIAL_SEQ",allocationSize=1)
    @GeneratedValue(generator = "SUM_MATERIAL_SEQ")
	@Column(name = "ID")
	private Long id;
	@Size(min = 1, max = 50)
	@Column(name = "DESCRIPCION", nullable = false)
	private String descripcion;
	@Column(name = "UNI_MATERIAL", nullable = false)
	private Long uni_material;
	@Column(name = "CANT_MAX", nullable = false)
	private Long cantidad_maxima;
	@Column(name = "CANT_MEDIA", nullable = false)
	private Long cantidad_media;
	@Column(name = "CANT_MINIMA", nullable = false)
	private Long cantidad_minima;
	@Column(name = "CANT_STOCK", nullable = false)
	private Long cantidad_stock;
	@Column(name = "PRECIO_PP", nullable = false)
	private BigDecimal precio_pp;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_MAT", nullable = false)
	private TipoMaterial tipo_material;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RUBRO_MAT", nullable = false)
	private Rubro rubro;
	@Size(min = 1, max = 50)
	@Column(name = "COD_MAT_EXT", nullable = false)
	private String codigo_material_externo;
	@Column(name = "URGENTE", nullable = true)
	private Long urgente;


	public Long getCantidad_maxima() {
		return cantidad_maxima;
	}

	public void setCantidad_maxima(Long cantidad_maxima) {
		this.cantidad_maxima = cantidad_maxima;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCantidad_stock() {
		return cantidad_stock;
	}

	public void setCantidad_stock(Long cantidad_stock) {
		this.cantidad_stock = cantidad_stock;
	}

	public Long getCantidad_minima() {
		return cantidad_minima;
	}

	public void setCantidad_minima(Long cantidad_minima) {
		this.cantidad_minima = cantidad_minima;
	}

	public Long getCantidad_media() {
		return cantidad_media;
	}

	public void setCantidad_media(Long cantidad_media) {
		this.cantidad_media = cantidad_media;
	}

	public BigDecimal getPrecio_pp() {
		return precio_pp;
	}

	public void setPrecio_pp(BigDecimal precio_pp) {
		this.precio_pp = precio_pp;
	}

	public TipoMaterial getTipo_material() {
		return tipo_material;
	}

	public void setTipo_material(TipoMaterial tipo_material) {
		this.tipo_material = tipo_material;
	}

	public String getCodigo_material_externo() {
		return codigo_material_externo;
	}

	public void setCodigo_material_externo(String codigo_material_externo) {
		this.codigo_material_externo = codigo_material_externo;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getUni_material() {
		return uni_material;
	}

	public void setUni_material(Long uni_material) {
		this.uni_material = uni_material;
	}
	
	public Long getUrgente() {
		return urgente;
	}

	public void setUrgente(Long urgente) {
		this.urgente = urgente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cantidad_maxima == null) ? 0 : cantidad_maxima.hashCode());
		result = prime * result + ((cantidad_media == null) ? 0 : cantidad_media.hashCode());
		result = prime * result + ((cantidad_minima == null) ? 0 : cantidad_minima.hashCode());
		result = prime * result + ((cantidad_stock == null) ? 0 : cantidad_stock.hashCode());
		result = prime * result + ((codigo_material_externo == null) ? 0 : codigo_material_externo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((precio_pp == null) ? 0 : precio_pp.hashCode());
		result = prime * result + ((rubro == null) ? 0 : rubro.hashCode());
		result = prime * result + ((tipo_material == null) ? 0 : tipo_material.hashCode());
		result = prime * result + ((uni_material == null) ? 0 : uni_material.hashCode());
		result = prime * result + ((urgente == null) ? 0 : urgente.hashCode());
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
		Material other = (Material) obj;
		if (cantidad_maxima == null) {
			if (other.cantidad_maxima != null)
				return false;
		} else if (!cantidad_maxima.equals(other.cantidad_maxima))
			return false;
		if (cantidad_media == null) {
			if (other.cantidad_media != null)
				return false;
		} else if (!cantidad_media.equals(other.cantidad_media))
			return false;
		if (cantidad_minima == null) {
			if (other.cantidad_minima != null)
				return false;
		} else if (!cantidad_minima.equals(other.cantidad_minima))
			return false;
		if (cantidad_stock == null) {
			if (other.cantidad_stock != null)
				return false;
		} else if (!cantidad_stock.equals(other.cantidad_stock))
			return false;
		if (codigo_material_externo == null) {
			if (other.codigo_material_externo != null)
				return false;
		} else if (!codigo_material_externo.equals(other.codigo_material_externo))
			return false;
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
		if (precio_pp == null) {
			if (other.precio_pp != null)
				return false;
		} else if (!precio_pp.equals(other.precio_pp))
			return false;
		if (rubro == null) {
			if (other.rubro != null)
				return false;
		} else if (!rubro.equals(other.rubro))
			return false;
		if (tipo_material == null) {
			if (other.tipo_material != null)
				return false;
		} else if (!tipo_material.equals(other.tipo_material))
			return false;
		if (uni_material == null) {
			if (other.uni_material != null)
				return false;
		} else if (!uni_material.equals(other.uni_material))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Material [id=" + id + ", descripcion=" + descripcion + ", uni_material=" + uni_material
				+ ", cantidad_maxima=" + cantidad_maxima + ", cantidad_media=" + cantidad_media + ", cantidad_minima="
				+ cantidad_minima + ", cantidad_stock=" + cantidad_stock + ", precio_pp=" + precio_pp
				+ ", tipo_material=" + tipo_material + ", rubro=" + rubro + ", codigo_material_externo="
				+ codigo_material_externo + ", urgente=" + urgente + "]";
	}
}