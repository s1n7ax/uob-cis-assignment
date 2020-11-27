package org.s1n7ax.feedback.entity;

public class Rating {

	private String quality;
	private double rating;

	public Rating() {
	}
	
	public Rating(String quality, double rating) {
		this.quality = quality;
		this.rating = rating;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
