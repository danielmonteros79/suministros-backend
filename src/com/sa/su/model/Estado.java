package com.sa.su.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUM_ESTADO")
public class Estado extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "estado_sequence", sequenceName = "SUM_ESTADO_SEQ")
	@GeneratedValue(generator = "estado_sequence")
	@Column(name = "ID")
	private Long id;
	
	@Size(min = 1, max = 50)
	@Column(name = "DESCRIPCION", nullable = false)
	private String descripcion;

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

}