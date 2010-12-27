package com.dealerlocator;

public class Coordinate {
	private float xValue;
	private float yValue;
	private String dealerName;
	private String address;
	private String city;
	private String state;
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	private String zip;

	public Coordinate(){
		super();
	}
	public Coordinate(float x, float y) {
		xValue = x;
		yValue = y;
	}

	public float getxValue() {
		return xValue;
	}

	public void setxValue(float xValue) {
		this.xValue = xValue;
	}

	public float getyValue() {
		return yValue;
	}

	public void setyValue(float yValue) {
		this.yValue = yValue;
	}
	public int getxValueMicroDeg(){
		return (int) (xValue *  1000000);
	}
	public int getyValueMicroDeg(){
		return (int) (yValue * 1000000);
	}

}
