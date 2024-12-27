package model;

public class Attribute {
	private double priceWeight;
	private double sizeWeight;
	private double locationScoreWeight;
	private double schoolScoreWeight;
	private double securityScoreWeight;
	private double hoaWeight;
	
	public Attribute(double priceWeight, double sizeWeight, double locationScoreWeight, 
			double schoolScoreWeight, double securityScoreWeight, double hoaWeight) {
		this.priceWeight = priceWeight;
		this.sizeWeight = sizeWeight;
		this.locationScoreWeight = locationScoreWeight;
		this.schoolScoreWeight = schoolScoreWeight;
		this.securityScoreWeight = securityScoreWeight;
		this.hoaWeight = hoaWeight;
	}
	
	public double getPriceWeight() {
		return priceWeight;
	}
	
	public double getSizeWeight() {
		return sizeWeight;
	}
	
	public double getLocationScoreWeight() {
		return locationScoreWeight;
	}
	
	public double getSchoolScoreWeight() {
		return schoolScoreWeight;
	}
	
	public double getSecurityScoreWeight() {
		return securityScoreWeight;
	}
	
	public double getHoaWeight() {
		return hoaWeight;
	}
}
