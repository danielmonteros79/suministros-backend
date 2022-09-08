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
@Table(name = "SUM_OBSERVACIONES")
public class Observacion implements java.io.Serializable {
	private static final long serialVersionUID = 20180319L;
	
	@Id
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ID_PEDIDO")
	@ForeignKey(name = "FK_OBSERVACION_PEDIDO")
	private Pedido id_pedido;
	
	@Id
	@Column(name = "OPERACION", nullable = false)
	private String operacion;
	
	@ManyToOne
	@JoinColumn(name = "ID_JUSTIFICACION")
	@ForeignKey(name = "FK_OBSERVACION_JUSTIFICACION")
	private Justificacion id_justificacion;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;

	public Pedido getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Pedido id_pedido) {
		this.id_pedido = id_pedido;
	}

	public Justificacion getId_justificacion() {
		return id_justificacion;
	}

	public void setId_justificacion(Justificacion id_justificacion) {
		this.id_justificacion = id_justificacion;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
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
		int result = 1;
		result = prime * result + ((id_pedido == null) ? 0 : id_pedido.hashCode());
		result = prime * result + ((operacion == null) ? 0 : operacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observacion other = (Observacion) obj;
		if (id_pedido == null) {
			if (other.id_pedido != null)
				return false;
		} else if (!id_pedido.equals(other.id_pedido))
			return false;
		if (operacion == null) {
			if (other.operacion != null)
				return false;
		} else if (!operacion.equals(other.operacion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Observacion [id_pedido=" + id_pedido + ", id_justificacion=" + id_justificacion + ", operacion=" + operacion + ", descripcion="
				+ descripcion + "]";
	}

}
