package com.sa.su.model;

import java.io.Serializable;

public class TipoAlertaPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	private TipoAlerta tipoAlerta;
	private Periodo periodo;

	public TipoAlertaPeriodo() {
	}

	public TipoAlertaPeriodo(TipoAlerta tipoAlerta, Periodo periodo) {
		this.tipoAlerta = tipoAlerta;
		this.periodo = periodo;
	}

	public TipoAlerta getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlerta tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	@Override
	public String toString() {
		return "TipoAlerta [tipoAlerta=" + tipoAlerta + ", periodo=" + periodo + "]";
	}

}
