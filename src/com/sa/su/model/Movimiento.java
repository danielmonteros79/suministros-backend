package com.sa.su.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SUM_MOVIMIENTOS")
public class Movimiento extends BaseEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SUM_MOVIMIENTOS_SEQ", sequenceName = "SUM_MOVIMIENTOS_SEQ")
	@GeneratedValue(generator = "SUM_MOVIMIENTOS_SEQ")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "MATERIAL_ID", nullable = false)
	private Long material;
	
	
	@Column(name = "CANTIDAD", nullable = false)
	private Long cantidad;
	
	@Column(name = "PRECIO_PP", nullable = false)
	private BigDecimal precio_pp;
	
	@Column(name = "TIPO_MOVIMIENTO", nullable = false)
	private String tipo_movimiento;
	
	@Column(name = "CENTRO_COSTOS_ID", nullable = false)
	private Long ccostos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterial() {
		return material;
	}

	public void setMaterial(Long material) {
		this.material = material;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio_pp() {
		return precio_pp;
	}

	public void setPrecio_pp(BigDecimal precio_pp) {
		this.precio_pp = precio_pp;
	}

	public String getTipo_movimiento() {
		return tipo_movimiento;
	}

	public void setTipo_movimiento(String tipo_movimiento) {
		this.tipo_movimiento = tipo_movimiento;
	}

	public Long getCcostos() {
		return ccostos;
	}

	public void setCcostos(Long ccostos) {
		this.ccostos = ccostos;
	}

}
