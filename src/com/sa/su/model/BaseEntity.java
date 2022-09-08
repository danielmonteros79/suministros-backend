package com.sa.su.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
class BaseEntity {
	

	@Column(name = "FE_ALTA", columnDefinition = "date default sysdate")
	@Temporal(TemporalType.DATE)
	@JsonIgnore
	protected Date fechaAlta;
	@Column(name = "FE_MOD")
	@Temporal(TemporalType.DATE)
	@JsonIgnore
	protected Date fechaMod;
	@Column(name = "FE_BAJA")
	@Temporal(TemporalType.DATE)
	@JsonIgnore
	protected Date fechaBaja;
	@Size(min = 1, max = 8)
	@Column(name = "USR_ALTA")
	@JsonIgnore
	protected String usuarioAlta;
	@Size(min = 1, max = 8)
	@Column(name = "USR_MOD")
	@JsonIgnore
	protected String usuarioMod;
	@Size(min = 1, max = 8)
	@Column(name = "USR_BAJA")
	@JsonIgnore
	protected String usuarioBaja;
	
	@PrePersist
	private void onPersistCallback() {
	  // just set the value here
	  // this will only ever be called once, on a Persist event which
	  // is when the insert occurs.  
//	  this.fechaAlta = new Date();
	}
    //getters and setters.
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(Date fechaMod) {
		this.fechaMod = fechaMod;
	}
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public String getUsuarioMod() {
		return usuarioMod;
	}
	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getUsuarioBaja() {
		return usuarioBaja;
	}
	public void setUsuarioBaja(String usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((fechaMod == null) ? 0 : fechaMod.hashCode());
		result = prime * result + ((usuarioAlta == null) ? 0 : usuarioAlta.hashCode());
		result = prime * result + ((usuarioBaja == null) ? 0 : usuarioBaja.hashCode());
		result = prime * result + ((usuarioMod == null) ? 0 : usuarioMod.hashCode());
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
		BaseEntity other = (BaseEntity) obj;
		if (fechaAlta == null) {
			if (other.fechaAlta != null)
				return false;
		} else if (!fechaAlta.equals(other.fechaAlta))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (fechaMod == null) {
			if (other.fechaMod != null)
				return false;
		} else if (!fechaMod.equals(other.fechaMod))
			return false;
		if (usuarioAlta == null) {
			if (other.usuarioAlta != null)
				return false;
		} else if (!usuarioAlta.equals(other.usuarioAlta))
			return false;
		if (usuarioBaja == null) {
			if (other.usuarioBaja != null)
				return false;
		} else if (!usuarioBaja.equals(other.usuarioBaja))
			return false;
		if (usuarioMod == null) {
			if (other.usuarioMod != null)
				return false;
		} else if (!usuarioMod.equals(other.usuarioMod))
			return false;
		return true;
	}
}
