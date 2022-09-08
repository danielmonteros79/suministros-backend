package com.sa.su.model;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "SUM_ALERTA", uniqueConstraints = @UniqueConstraint(name = "UK_ALERTA", columnNames = { "material_id", "centro_costos_id", "periodo_id",
		"tipo_alerta_id", "fe_baja" }))
public class Alerta extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "alerta_sequence", sequenceName = "ALERTA_SEQ")
	@GeneratedValue(generator = "alerta_sequence")
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "MATERIAL_ID", nullable = false)
	@ForeignKey(name = "FK_ALERTA_MATERIAL")
	private Material material;

	@ManyToOne
	@JoinColumn(name = "CENTRO_COSTOS_ID", nullable = false)
	@ForeignKey(name = "FK_ALERTA_CC")
	private CentroCostos centroCostos;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERIODO_ID", nullable = false)
	@ForeignKey(name = "FK_ALERTA_PERIODO")
	private Periodo periodo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_ALERTA_ID", nullable = false)
	@ForeignKey(name = "FK_ALERTA_TIPO_ALERTA")
	private TipoAlerta tipoAlerta;

	@Column(name = "VALOR", nullable = false)
	private BigDecimal valor;

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public TipoAlerta getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlerta tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public CentroCostos getCentroCostos() {
		return centroCostos;
	}

	public void setCentroCostos(CentroCostos centroCostos) {
		this.centroCostos = centroCostos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		Alerta other = (Alerta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Alerta [id=" + id + ", material.id=" + material.getId() + ", centroCostos.id=" + centroCostos.getId() + ", periodo.id=" + periodo.getId()
				+ ", tipoAlerta=" + tipoAlerta + ", valor=" + valor + "]";
	}

}
