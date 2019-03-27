package com.page.map.dto;

public class MapDto {

	private int mapno;
	private double latitude;
	private double longitude;
	private String addr;
	private String jibun;
	
	public MapDto(double latitude, double longitude, String addr, String jibun) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.addr = addr;
		this.jibun = jibun;
	}

	public MapDto() {
		super();
	}

	public int getMapno() {
		return mapno;
	}

	public void setMapno(int mapno) {
		this.mapno = mapno;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getJibun() {
		return jibun;
	}

	public void setJibun(String jibun) {
		this.jibun = jibun;
	}	
	
}