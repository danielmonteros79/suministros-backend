package com.sa.su.beans;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class CvsTratamiento {
	
	

    @CsvBindByName(column = "Nro Pedido Tratamiento                    ", required = true)
    @CsvBindByPosition(position = 0)
    private String nroPedidoTratamiento;
    
    @CsvBindByName(column = "Id Pedido", required = true)
    @CsvBindByPosition(position = 1)
    private String idPedido;
    
	@CsvBindByName(column = "Material", required = true)
	@CsvBindByPosition(position = 2)
    private String idMaterial;

    @CsvBindByName(column = "Descripción", required = true)
    @CsvBindByPosition(position = 3)
    private String descripcion;
	
    @CsvBindByName(column = "Cantidad", required = true)
    @CsvBindByPosition(position = 4)
    private String cantAprobada;
    
    @CsvBindByName(column = "Unidades", required = true)
    @CsvBindByPosition(position = 5)
    private String unidades;
    

        
    @CsvBindByName(column = "Nro CCosto", required = true)
    @CsvBindByPosition(position = 6)
    private String idCentroCosto;
    
    @CsvBindByName(column = "Dirección", required = false)
    @CsvBindByPosition(position = 7)
    private String direccion1;
    
    @CsvBindByName(column = "Localidad", required = false)
    @CsvBindByPosition(position = 8)
    private String direccion2;
    
    @CsvBindByName(column = "Provincia", required = false)
    @CsvBindByPosition(position = 9)
    private String direccion3;
    
    @CsvBindByName(column = "Codigo Postal", required = false)
    @CsvBindByPosition(position = 10)
    private String codpostal;
    
    @CsvBindByName(column = "Monto", required = true)
    @CsvBindByPosition(position = 11)
    private String monto;
    
    @CsvBindByName(column = "Fecha Alta", required = true)
    @CsvBindByPosition(position = 12)
    private String fechaAlta;
    
    @CsvBindByName(column = "Fecha Tratamiento", required = true)
    @CsvBindByPosition(position = 13)
    private String fechaTratamiento;
    
	
	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getCodpostal() {
		return codpostal;
	}

	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}

	public String getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(String idMaterial) {
		this.idMaterial = idMaterial;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCantAprobada() {
		return cantAprobada;
	}

	public void setCantAprobada(String cantAprobada) {
		this.cantAprobada = cantAprobada;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}

	public String getIdCentroCosto() {
		return idCentroCosto;
	}

	public void setIdCentroCosto(String idCentroCosto) {
		this.idCentroCosto = idCentroCosto;
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

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public String getNroPedidoTratamiento() {
		return nroPedidoTratamiento;
	}

	public void setNroPedidoTratamiento(String nroPedidoTratamiento) {
		this.nroPedidoTratamiento = nroPedidoTratamiento;
	}

	public String getFechaTratamiento() {
		return fechaTratamiento;
	}

	public void setFechaTratamiento(String fechaTratamiento) {
		this.fechaTratamiento = fechaTratamiento;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}