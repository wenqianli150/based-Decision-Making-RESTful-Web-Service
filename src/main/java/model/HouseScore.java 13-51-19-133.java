package model;

public class HouseScore {
	private String name;
	private double score;
	
	public HouseScore(String name, double score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public double getScore() {
		return score;
	}
}
