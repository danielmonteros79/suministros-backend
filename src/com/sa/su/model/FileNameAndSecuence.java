package com.sa.su.model;

public class FileNameAndSecuence implements java.io.Serializable{
	
	private static final long serialVersionUID = 20180315L;
	
	private String name;
	private Long sec;
	
	public FileNameAndSecuence() {	}
	
	public FileNameAndSecuence(String name, Long sec) {
		this.name = name;
		this.sec = sec;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSec() {
		return sec;
	}
	public void setSec(Long sec) {
		this.sec = sec;
	}
}
