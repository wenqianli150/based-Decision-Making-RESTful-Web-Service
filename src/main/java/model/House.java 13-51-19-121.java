package model;

public class House {
	private String name;
	private int price;
	private int size;
	private int locationScore;
	private int schoolScore;
	private int securityScore;
	private int hoa;
	
	public House(String name, int price, int size, int locationScore, 
			int schoolScore, int securityScore, int hoa) {
		this.name = name;
		this.price = price;
		this.size = size;
		this.locationScore = locationScore;
		this.schoolScore = schoolScore;
		this.securityScore = securityScore;
		this.hoa = hoa;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getLocationScore() {
		return locationScore;
	}
	
	public int getSchoolScore() {
		return schoolScore;
	}
	
	public int getSecurityScore() {
		return securityScore;
	}
	
	public int getHoa() {
		return hoa;
	}
}
