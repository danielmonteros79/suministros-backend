package com.sa.su.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "SUM_PEDIDO")
public class Pedido extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="pedido_sequence",sequenceName="SUM_PEDIDO_SEQ")
    @GeneratedValue(generator = "pedido_sequence")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MATERIAL_ID")
	private Material material;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRIORIDAD_ID")
	private Prioridad prioridad;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CENTRO_COSTOS_ID")
	private CentroCostos ccostos;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ESTADO_ID")
	private Estado estado;

	@JoinColumn(name = "FE_SOLICITUD")
	@Temporal(TemporalType.DATE)
	private Date fe_solicitud;

	@JoinColumn(name = "CANTIDAD")
	private Long cantidad;

	@JoinColumn(name = "FE_TRATAMIENTO")
	@Temporal(TemporalType.DATE)
	private Date fe_tratamiento;
	
	@Column(name = "NUM_TRATAMIENTO")
	private Long num_tratamiento;

	@JoinColumn(name = "CANT_APROBADA")
	private Long cant_aprobada;

	@JoinColumn(name = "FE_SOLICITUD")
	@Temporal(TemporalType.DATE)
	private Date fe_recepcion;

	@JoinColumn(name = "CANTIDAD_RECEPCION")
	private Long cantidad_recepcion;

	@JoinColumn(name = "FE_ENTREGA")
	@Temporal(TemporalType.DATE)
	private Date fe_entrega;
	
	@JoinColumn(name = "COD_USUARIO_RECEPCION")
	private String cod_usuario_recepcion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VALIDACION_ID")
	private Validacion validacion;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy="id_pedido")
	private Set<Observacion> observaciones = new HashSet<Observacion>();
	
	public Pedido() {}
	
	public Pedido(Long id) {
		this.id = id;
	}

	public Set<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(Set<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public Validacion getValidacion() {
		return validacion;
	}

	public void setValidacion(Validacion validacion) {
		this.validacion = validacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Prioridad getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}

	public CentroCostos getCcostos() {
		return ccostos;
	}

	public void setCcostos(CentroCostos ccostos) {
		this.ccostos = ccostos;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Date getFe_solicitud() {
		return fe_solicitud;
	}

	public void setFe_solicitud(Date fe_solicitud) {
		this.fe_solicitud = fe_solicitud;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFe_tratamiento() {
		return fe_tratamiento;
	}

	public void setFe_tratamiento(Date fe_tratamiento) {
		this.fe_tratamiento = fe_tratamiento;
	}

	public Long getCant_aprobada() {
		return cant_aprobada;
	}

	public void setCant_aprobada(Long cant_aprobada) {
		this.cant_aprobada = cant_aprobada;
	}

	public Date getFe_recepcion() {
		return fe_recepcion;
	}

	public void setFe_recepcion(Date fe_recepcion) {
		this.fe_recepcion = fe_recepcion;
	}

	public String getCod_usuario_recepcion() {
		return cod_usuario_recepcion;
	}

	public void setCod_usuario_recepcion(String cod_usuario_recepcion) {
		this.cod_usuario_recepcion = cod_usuario_recepcion;
	}

	public Long getCantidad_recepcion() {
		return cantidad_recepcion;
	}

	public void setCantidad_recepcion(Long cantidad_recepcion) {
		this.cantidad_recepcion = cantidad_recepcion;
	}

	public Date getFe_entrega() {
		return fe_entrega;
	}

	public void setFe_entrega(Date fe_entrega) {
		this.fe_entrega = fe_entrega;
	}

	public Long getNum_tratamiento() {
		return num_tratamiento;
	}

	public void setNum_tratamiento(Long num_tratamiento) {
		this.num_tratamiento = num_tratamiento;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", material=" + material + ", prioridad=" + prioridad + ", ccostos=" + ccostos + ", estado=" + estado
				+ ", fe_solicitud=" + fe_solicitud + ", cantidad=" + cantidad + ", fe_tratamiento=" + fe_tratamiento + ", num_tratamiento="
				+ num_tratamiento + ", cant_aprobada=" + cant_aprobada + ", fe_recepcion=" + fe_recepcion + ", cantidad_recepcion="
				+ cantidad_recepcion + ", fe_entrega=" + fe_entrega + ", cod_usuario_recepcion=" + cod_usuario_recepcion + ", validacion="
				+ validacion + ", observaciones=" + observaciones + "]";
	}
}