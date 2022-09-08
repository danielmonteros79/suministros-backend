package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SUM_CONSUMO_MATERIAL")
public class ConsumoMaterial extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 20181204L;

	@Id
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ID_MATERIAL")
	@ForeignKey(name = "FK_CONSUMO_MATERIAL_MATERIAL")
	private Material material;

	@Id
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ID_CONSUMO")
	@ForeignKey(name = "FK_CONSUMO_MATERIAL_CONSUMO")
	private Consumo consumo;

	@Column(name = "CANT_MAX_PEDIDO")
	private Long cant_max_pedido;

	@Column(name = "CANT_MAX_ANUAL")
	private Long cant_max_anual;

	public Material getId_material() {
		return material;
	}

	public void setId_material(Material id_material) {
		this.material = id_material;
	}

	public Consumo getId_consumo() {
		return consumo;
	}

	public void setId_consumo(Consumo id_consumo) {
		this.consumo = id_consumo;
	}

	public Long getCant_max_pedido() {
		return cant_max_pedido;
	}

	public void setCant_max_pedido(Long cant_max_pedido) {
		this.cant_max_pedido = cant_max_pedido;
	}

	public Long getCant_max_anual() {
		return cant_max_anual;
	}

	public void setCant_max_anual(Long cant_max_anual) {
		this.cant_max_anual = cant_max_anual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cant_max_anual == null) ? 0 : cant_max_anual.hashCode());
		result = prime * result + ((cant_max_pedido == null) ? 0 : cant_max_pedido.hashCode());
		result = prime * result + ((consumo == null) ? 0 : consumo.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
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
		ConsumoMaterial other = (ConsumoMaterial) obj;
		if (cant_max_anual == null) {
			if (other.cant_max_anual != null)
				return false;
		} else if (!cant_max_anual.equals(other.cant_max_anual))
			return false;
		if (cant_max_pedido == null) {
			if (other.cant_max_pedido != null)
				return false;
		} else if (!cant_max_pedido.equals(other.cant_max_pedido))
			return false;
		if (consumo == null) {
			if (other.consumo != null)
				return false;
		} else if (!consumo.equals(other.consumo))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConsumoMaterial [material=" + material + ", consumo=" + consumo + ", cant_max_pedido=" + cant_max_pedido
				+ ", cant_max_anual=" + cant_max_anual + "]";
	}

}