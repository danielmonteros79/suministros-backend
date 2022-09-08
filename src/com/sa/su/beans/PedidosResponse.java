package com.sa.su.beans;

import java.util.List;

import com.sa.su.model.Pedido;

public class PedidosResponse implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7728663713460849070L;
	private List<Pedido> pendiente;
	private List<Pedido> recepcion;
	private List<Pedido> resuelto;
	public List<Pedido> getPendiente() {
		return pendiente;
	}
	public void setPendiente(List<Pedido> pendiente) {
		this.pendiente = pendiente;
	}
	public List<Pedido> getRecepcion() {
		return recepcion;
	}
	public void setRecepcion(List<Pedido> recepcion) {
		this.recepcion = recepcion;
	}
	public List<Pedido> getResuelto() {
		return resuelto;
	}
	public void setResuelto(List<Pedido> resuelto) {
		this.resuelto = resuelto;
	}
	
	

}
